import os
import json
import re

EMPTY = {'':[['','']]}

def camel_to_spaced(text):
    spaced = re.sub(r'(?<!^)(?=[A-Z])', ' ', text).lower()
    return spaced

def convert_type(type):
    if type == "byte" or type == "int" or type == "long" or type == "short":
        return "Number"
    if type == "boolean":
        return "Boolean"
    if type == "java.lang.String":
        return "String"
    if "[]" in type or "List<" in type or "Set<" in type or "Collection<" in type or "Map<" in type or "Iterator<" in type:
        return "Array"
    if type == "void":
        return ""
    return type

def fully_converted_type(return_type):
    global hierarchy
    converted_type = convert_type(return_type)
    if converted_type in hierarchy:
        return ','.join(hierarchy[converted_type])
    return converted_type

def categorize_methods(methods, ereditate_methods=([],[],[],[])):
    getters, boolean_getters, setters, unsupported = ereditate_methods
    
    for method in methods:
        name = method["name"]
        return_type = method["returnType"]
        arguments = method.get("arguments", [])
        package_name = method.get("packageName", "")

        converted_return_type = fully_converted_type(return_type)
        converted_arguments_types = ";".join([fully_converted_type(arg["type"]) for arg in arguments])
        full_name = f'{name};{converted_arguments_types};{converted_return_type}'
        
        if len(arguments) == 0:
            if return_type == "boolean":
                boolean_getters.append([camel_to_spaced(name.replace("is", "")), full_name])
            else:
                getters.append([camel_to_spaced(name.replace("get", "")), full_name])
        elif len(arguments) == 1 and return_type == "void":
            setters.append([camel_to_spaced(name.replace("set", "")), full_name])
        else:
            arg_types = ', '.join([arg["type"] for arg in arguments])
            unsupported.append([camel_to_spaced(name), name, f'returns: {return_type}; argument types: {arg_types}']) 

    if not getters: getters = [['','']]
    if not boolean_getters: boolean_getters = [['','']]
    if not setters: setters = [['','']]

    return getters, boolean_getters, setters, unsupported

def process_file(file_path, categorized_methods):
    with open(file_path, "r") as file:
        data = json.load(file)
        class_name = data["className"]
        package_name = data["packageName"]
        full_type = f'{package_name}.{class_name}'
        methods = data.get("methods", [])
        categorized_methods = categorize_methods(methods, categorized_methods)

        if "extendsList" in data:
            for parent_class_name in data["extendsList"]:
                path = "/".join(parent_class_name.split("."))
                parent_file_path = f"./interfaces/{path}.json"
                if os.path.exists(parent_file_path):
                    categorized_methods = process_file(parent_file_path, categorized_methods)[1]

        return full_type, categorized_methods

def generateHierarchy():
    input_folders = [
        "./interfaces/"
    ]
    global hierarchy
    hierarchy = {
        "Number": ["Number"],
        "Boolean": ["Boolean"],
        "String": ["String"],
        "Array": ["Array"]
    }
    for input_folder in input_folders:
        for root, subdirs, files in os.walk(input_folder):
            for file_name in files:
                if file_name.endswith(".json"):
                    file_path = os.path.join(root, file_name)
                    with open(file_path, "r") as file:
                        data = json.load(file)
                        class_name = data["className"]
                        package_name = data["packageName"]
                        full_type = f'{package_name}.{class_name}'
                        hierarchy[full_type] = data.get("extendsList", [])
                        hierarchy[full_type].insert(0, full_type)
    while True:
        added = False
        for class_name, parents in hierarchy.items():
            for parent in parents:
                if parent in hierarchy:
                    for grandparent in hierarchy[parent]:
                        if grandparent not in hierarchy[class_name]:
                            hierarchy[class_name].append(grandparent)
                            added = True
        if not added:
            break
    return hierarchy

def main():
    input_folders = [
        "./interfaces/"
    ]
    output_file = "game_actions.json"
    
    actions = []
    getters_dict = EMPTY.copy()
    boolean_getters_dict = EMPTY.copy()
    setters_dict = EMPTY.copy()
    unsupported_dict = {}

    generateHierarchy()
    
    for input_folder in input_folders:
        for root, subdirs, files in os.walk(input_folder):
            for file_name in files:
                if file_name.endswith(".json"):
                    file_path = os.path.join(root, file_name)
                    full_name, (getters, boolean_getters, setters, unsupported) = process_file(file_path, ([],[],[],[]))
                    class_name = full_name.split(".")[-1]
                    actions.append([camel_to_spaced(class_name), full_name])
                    getters_dict[full_name] = getters
                    boolean_getters_dict[full_name] = boolean_getters
                    setters_dict[full_name] = setters
                    if unsupported: unsupported_dict[full_name] = unsupported
            
    result = {
        "actions": actions,
        "getters": getters_dict,
        "boolean_getters": boolean_getters_dict,
        "setters": setters_dict,
        "unsupported": unsupported_dict
    }
    
    with open(output_file, "w") as out_file:
        json.dump(result, out_file, indent=4)

if __name__ == "__main__":
    main()
