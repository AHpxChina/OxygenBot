package utils

import modules.command.CommandBase

fun CommandBase.getParameters(raw : String) : List<String>{
    var re = raw;

    for (executor in this.executors) {
        re = re.substringAfter(executor)
    }

    return re.trim().split(" ")
}