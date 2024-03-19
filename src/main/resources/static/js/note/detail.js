const NOTE_ID = window.location.pathname.split("/")[2];


async function deleteNote() {
    const response = await fetch(`http://localhost:8080/api/note/${NOTE_ID}`, {
        method: "DELETE",
    });

    if (!response.ok) {
        return alert("노트 삭제를 실패했습니다");
    }

    window.location.href = `/note`;
}