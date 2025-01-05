import os
import json
import re

EMPTY = {'':[['','']]}

def camel_to_spaced(text):
    spaced = re.sub(r'(?<!^)(?=[A-Z])', ' ', text).lower()
    return spaced

def categorize_methods(methods):
    getters, boolean_getters, setters, unsupported = [], [], [], []
    
    for method in methods:
        name = method["name"]
        return_type = method["returnType"]
        arguments = method.get("arguments", [])
        
        if len(arguments) == 0:
            if return_type == "boolean":
                boolean_getters.append([camel_to_spaced(name.replace("is", "")), name])
            else:
                getters.append([camel_to_spaced(name.replace("get", "")), name])
        elif len(arguments) == 1 and return_type == "void":
            setters.append([camel_to_spaced(arguments[0]["name"]), name])
        else:
            arg_types = ', '.join([arg["type"] for arg in arguments])
            unsupported.append([camel_to_spaced(name), name, f'returns: {return_type}; argument types: {arg_types}'])
    
    return getters, boolean_getters, setters, unsupported

def process_file(file_path):
    with open(file_path, "r") as file:
        data = json.load(file)
        class_name = data["className"]
        methods = data.get("methods", [])
        return class_name, categorize_methods(methods)

def main():
    input_folder = "./interfaces"
    output_file = "game_events.json"
    
    events = []
    getters_dict = EMPTY.copy()
    boolean_getters_dict = EMPTY.copy()
    setters_dict = EMPTY.copy()
    unsupported_dict = {}
    
    for file_name in os.listdir(input_folder):
        if file_name.endswith(".json"):
            file_path = os.path.join(input_folder, file_name)
            class_name, (getters, boolean_getters, setters, unsupported) = process_file(file_path)
            
            events.append([camel_to_spaced(class_name.replace("Event", "")), class_name])
            getters_dict[class_name] = getters
            boolean_getters_dict[class_name] = boolean_getters
            setters_dict[class_name] = setters
            if unsupported: unsupported_dict[class_name] = unsupported
    
    result = {
        "events": events,
        "getters": getters_dict,
        "boolean_getters": boolean_getters_dict,
        "setters": setters_dict,
        "unsupported": unsupported_dict
    }
    
    with open(output_file, "w") as out_file:
        json.dump(result, out_file, indent=4)

if __name__ == "__main__":
    main()
