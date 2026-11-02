function openWindow() {
    document.getElementById("window").style.display = "block";
}

function closeWindow() {
    addTask();
    document.getElementById("window").style.display = "none";
}

async function addTask() {
    let taskStatus = document.getElementById("status").value;
    const taskPriority = document.getElementById("tPriority").value;
    const taskName = document.getElementById("name").value;
    const taskDescription = document.getElementById("tText").value;

    const response = await fetch("Servlet", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body: new URLSearchParams({
            status: taskStatus,
            priority: taskPriority,
            name: taskName,
            text: taskDescription
        })
    });

    const responseText = await response.text();
    const taskId = responseText.trim();
    console.log(responseText);

    const taskElement = document.createElement("div");

    const nameElement = document.createElement("div");
    nameElement.textContent = taskName;

    const descriptionElement = document.createElement("div");
    descriptionElement.textContent = taskDescription;

    const statusElement = document.createElement("div");
    const priorityElement = document.createElement("div");

    if (taskStatus == "3") {
        statusElement.textContent = "not started";
    } else if (taskStatus == "2") {
        statusElement.textContent = "started";
    } else if (taskStatus == "1") {
        statusElement.textContent = "done";
    }

    if (taskPriority == "3") {
        priorityElement.textContent = "not important";
    } else if (taskPriority == "2") {
        priorityElement.textContent = "important";
    } else {
        priorityElement.textContent = "very important";
    }

    taskElement.appendChild(nameElement);
    taskElement.appendChild(descriptionElement);
    taskElement.appendChild(statusElement);
    taskElement.appendChild(priorityElement);

    const changeStatusButton = document.createElement("button");
    changeStatusButton.textContent = "Change status";

    // Cycles the status: not started -> started -> done -> not started
    changeStatusButton.onclick = function () {
        if (taskStatus == "3") {
            taskStatus = "2";
            statusElement.textContent = "started";
        } else if (taskStatus == "2") {
            taskStatus = "1";
            statusElement.textContent = "done";
        } else if (taskStatus == "1") {
            taskStatus = "3";
            statusElement.textContent = "not started";
        }

        fetch("changeStatus", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams({
                id: taskId,
                status: taskStatus
            })
        });
    };

    taskElement.appendChild(changeStatusButton);

    document.getElementById("taskList").appendChild(taskElement);

    const deleteButton = document.createElement("button");
    deleteButton.textContent = "delete task";

    deleteButton.onclick = function () {
        taskElement.remove();

        fetch("deleteTasks", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams({ id: taskId })
        });
    };

    taskElement.appendChild(deleteButton);
}

window.onload = function () {

    fetch("getTasks")
        .then(response => response.text())
        .then(responseText => {

            const taskLines = responseText.split("\n");

            taskLines.forEach(taskLine => {

                if (taskLine.trim() == "") {
                    return;
                }

                // Line format: id|name|description|priority|status
                const fields = taskLine.split("|");

                const taskId = fields[0];
                const taskName = fields[1];
                const taskDescription = fields[2];
                const taskPriority = fields[3];
                let taskStatus = fields[4];

                const taskElement = document.createElement("div");

                const nameElement = document.createElement("div");
                nameElement.textContent = taskName;

                const descriptionElement = document.createElement("div");
                descriptionElement.textContent = taskDescription;

                const statusElement = document.createElement("div");
                const priorityElement = document.createElement("div");

                if (taskStatus == "3") {
                    statusElement.textContent = "not started";
                } else if (taskStatus == "2") {
                    statusElement.textContent = "started";
                } else if (taskStatus == "1") {
                    statusElement.textContent = "done";
                }

                if (taskPriority == "3") {
                    priorityElement.textContent = "not important";
                } else if (taskPriority == "2") {
                    priorityElement.textContent = "important";
                } else if (taskPriority == "1") {
                    priorityElement.textContent = "very important";
                }

                taskElement.appendChild(nameElement);
                taskElement.appendChild(descriptionElement);
                taskElement.appendChild(statusElement);
                taskElement.appendChild(priorityElement);

                const changeStatusButton = document.createElement("button");
                changeStatusButton.textContent = "Change status";

                changeStatusButton.onclick = function () {
                    if (taskStatus == "3") {
                        taskStatus = "2";
                        statusElement.textContent = "started";
                    } else if (taskStatus == "2") {
                        taskStatus = "1";
                        statusElement.textContent = "done";
                    } else {
                        taskStatus = "3";
                        statusElement.textContent = "not started";
                    }

                    fetch("changeStatus", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded"
                        },
                        body: new URLSearchParams({
                            id: taskId,
                            status: taskStatus
                        })
                    });
                };

                taskElement.appendChild(changeStatusButton);

                document.getElementById("taskList").appendChild(taskElement);

                const deleteButton = document.createElement("button");
                deleteButton.textContent = "delete task";

                deleteButton.onclick = function () {
                    taskElement.remove();

                    fetch("deleteTasks", {
                        method: "POST",
                        headers: { "Content-Type": "application/x-www-form-urlencoded" },
                        body: new URLSearchParams({ id: taskId })
                    });
                };

                taskElement.appendChild(deleteButton);
            });
        });
};