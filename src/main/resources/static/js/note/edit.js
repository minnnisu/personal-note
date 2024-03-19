const MAX_IMAGE_COUNT = 10;
const NOTE_ID = window.location.pathname.split("/")[3];

// 서버에 등록된 이미지 중 삭제할 이미지들
const deletionTargetImages = [];

window.onload = async function () {
    try {
        const response = await fetch(
            `http://localhost:8080/api/note/image/${NOTE_ID}`
        );

        if (!response.ok) {
            window.location.href = "/note";
            return alert("노트 정보를 불러오는 과정에서 오류가 발생하였습니다.");
        }

        const data = await response.json();

        const preview = document.getElementById("imagePreview");
        for (const image of data.imageNames) {
            const previewImage = document.createElement("img");
            previewImage.className = "preview_image";
            previewImage.src = `http://localhost:8080/images/note/${image}`;
            previewImage.isSaved = true;
            previewImage.imageName = image;

            const deleteBtnWrapper = document.createElement("button");
            deleteBtnWrapper.className = "delete_btn_wrapper";

            const deleteBtn = document.createElement("span");
            deleteBtn.className = "delete_btn material-symbols-outlined";
            deleteBtn.textContent = "close";
            deleteBtnWrapper.appendChild(deleteBtn);

            deleteBtnWrapper.onclick = function () {
                removeImage(previewImage);
            };

            const previewImageContainer = document.createElement("div");
            previewImageContainer.className = "preview_image_container";
            previewImageContainer.appendChild(previewImage);
            previewImageContainer.appendChild(deleteBtnWrapper);

            preview.appendChild(previewImageContainer);
        }
    } catch (error) {
        window.location.href = "/note";
        return alert("상품 정보를 불러오는 과정에서 오류가 발생하였습니다.");
    }
};

function selectProductImg() {
    const inputElement = document.getElementById("imageInput");

    // input 태그를 클릭
    inputElement.click();
}

function addImages() {
    const input = document.getElementById("imageInput");
    const files = input.files;
    const preview = document.getElementById("imagePreview");

    const totalImageCount = preview.childElementCount + files.length;
    // 카메라 버튼 포함하고 계산해야 되므로 +1 한다.
    if (totalImageCount > MAX_IMAGE_COUNT + 1) {
        return alert("이미지는 최대 10개까지만 등록할 수 있습니다");
    }

    for (const file of files) {
        const reader = new FileReader();

        reader.onload = function (e) {
            const previewImage = document.createElement("img");
            previewImage.className = "preview_image";
            previewImage.src = e.target.result;
            previewImage.isSaved = false;
            previewImage.imageFile = file;

            const deleteBtnWrapper = document.createElement("button");
            deleteBtnWrapper.className = "delete_btn_wrapper";

            const deleteBtn = document.createElement("span");
            deleteBtn.className = "delete_btn material-symbols-outlined";
            deleteBtn.textContent = "close";
            deleteBtnWrapper.appendChild(deleteBtn);

            deleteBtnWrapper.onclick = function () {
                removeImage(previewImage);
            };

            const previewImageContainer = document.createElement("div");
            previewImageContainer.className = "preview_image_container";
            previewImageContainer.appendChild(previewImage);
            previewImageContainer.appendChild(deleteBtnWrapper);

            preview.appendChild(previewImageContainer);
        };

        reader.readAsDataURL(file); // base64로 변환
    }
}

function removeImage(previewImage) {
    const preview = document.getElementById("imagePreview");
    if (previewImage.isSaved) {
        deletionTargetImages.push(previewImage.imageName);
    }
    preview.removeChild(previewImage.parentNode);
}


function checkProductDataInputValid(data) {
    const {title, content} = data;

    if (title === "") {
        throw new Error("제목을 입력해주세요.");
    }

    if (content.trim() === "") {
        throw new Error("본문을 입력해주세요.");
    }
}

async function editNote() {
    const formData = new FormData();
    const preview = document.getElementById("imagePreview");

    const title = document.getElementById("title").value; // 앞, 뒤 공백 제거
    const content = document.getElementById("content").value;

    try {
        checkProductDataInputValid({
            title,
            content,
        });

        for (let i = 3; i < preview.childNodes.length; i++) {
            const imgContainer = preview.childNodes[i];
            if (!imgContainer.childNodes[0].isSaved) {
                formData.append("files", imgContainer.childNodes[0].imageFile);
            }
        }

        const json = JSON.stringify({title, content, deletionTargetImages})
        const blob = new Blob([json], {type: "application/json"});
        formData.append("note", blob);

        const response = await fetch(`http://localhost:8080/api/note/${NOTE_ID}`, {
            method: "PUT",
            body: formData
        });

        const data = await response.json();

        if (!response.ok) {
            return alert(data.message);
        }

        window.location.href = `/note/${data.id}`;
    } catch (error) {
        return alert(
            error.message || "상품 정보를 수정하는 동안 오류가 발생하였습니다"
        );
    }
}
