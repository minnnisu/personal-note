async function saveNote() {
    const title = document.getElementById("title").value;
    const content = document.getElementById("content").value;

    const requestBody = {
        title: title,
        content: content
    }

    console.log(requestBody)

    const response = await fetch("http://localhost:8080/api/note", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    });

    if (!response.ok) {
        const responseBody = await response.json();
        alert(responseBody)
        return;
    }

    alert("성공적으로 노트를 추가하였습니다.");
    window.location.href = "/note"

}