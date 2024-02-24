const deleteBtns = document.querySelectorAll(".btn.btn-secondary.delete-btn")

deleteBtns.forEach(deleteBtn => {
    deleteBtn.addEventListener("click", async (e) => {
        const noteContainerNode = e.target.parentNode.parentNode.parentNode
        const noteId = noteContainerNode.getAttribute("value");

        const response = await fetch(`http://localhost:8080/api/note?id=${noteId}`,{
            method: "DELETE"
        })

        if(!response.ok){
            const responseBody = await response.json();
            return alert(responseBody.message);
        }

        alert("성공적으로 노트를 삭제하였습니다.")
        window.location.reload();
    })
})

async function saveNote() {
    const title = document.getElementById("note-input-modal-title").value;
    const content = document.getElementById("note-input-modal-content").value;

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