async function submitSubmitForm() {
    // 입력 필드의 값 가져오기
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const repeatedPassword = document.getElementById("repeated-password").value;
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;


    const formData = {
        username: username,
        password: password,
        repeatedPassword: repeatedPassword,
        name: name,
        email: email
    };

    const response = await fetch("http://localhost:8080/api/signup", {
        method: 'POST', // HTTP 메서드 설정
        headers: {
            'Content-Type': 'application/json' // 데이터 형식 지정 (JSON 형식)
        },
        body: JSON.stringify(formData) // 데이터를 JSON 문자열로 변환하여 body에 설정
    });

    if (!response.ok) {
       const data = await response.json();
       console.log(data)
        return;
    }

    alert("회원가입에 성공하였습니다");
    window.location.href = "/login"

}
