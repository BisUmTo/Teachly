import os
import json
import re

EMPTY = {'':[['','']]}

def camel_to_spaced(text):
    spaced = re.sub(r'(?<!^)(?=[A-Z])', ' ', text).lower()
    return spaced

def categorize_methods(methods, ereditated_methods=([],[],[],[])):
    getters, boolean_getters, setters, unsupported = ereditated_methods
    
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

def process_file(file_path, catgorized_methods):
    with open(file_path, "r") as file:
        data = json.load(file)
        class_name = data["className"]
        methods = data.get("methods", [])
        catgorized_methods = categorize_methods(methods, catgorized_methods)

        if "extendsList" in data:
            for parent_class_name in data["extendsList"]:
                path = "/".join(parent_class_name.split("."))
                parent_file_path = f"./interfaces/{path}.json"
                if os.path.exists(parent_file_path):
                    catgorized_methods = process_file(parent_file_path, catgorized_methods)[1]

        return class_name, catgorized_methods

def main():
    input_folders = [
        "./interfaces/com/destroystokyo/paper/event/player",
        "./interfaces/io/papermc/paper/event/player",
        "./interfaces/org/bukkit/event/player"
    ]
    output_file = "game_events.json"
    
    events = []
    getters_dict = EMPTY.copy()
    boolean_getters_dict = EMPTY.copy()
    setters_dict = EMPTY.copy()
    unsupported_dict = {}
    
    for input_folder in input_folders:
        for root, subdirs, files in os.walk(input_folder):
            for file_name in files:
                if file_name.endswith(".json"):
                    file_path = os.path.join(root, file_name)
                    class_name, (getters, boolean_getters, setters, unsupported) = process_file(file_path, ([],[],[],[]))
                    
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
