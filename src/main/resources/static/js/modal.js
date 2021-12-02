//새로고침마다 로그인 상태에 따라 로그인/아웃이 바뀜
$(document).ready(function () {
    if(localStorage.getItem("token") == null){
        $('#login-button').show()
        $('#usernames').hide()
        $('#logout-button').hide()
    } else {
        $('#usernames').show()
        $('#login-button').hide()
        $('#logout-button').show()
    }
});



// 비밀번호 숨기기/보기 기능
$('#login_password_eye').on("mousedown", function(){
    $('#login_password').attr('type',"text");
}).on('mouseup mouseleave', function() {
    $('#login_password').attr('type',"password");
});

$('#signup_password_eye').on("mousedown", function(){
    $('#signup_password').attr('type',"text");
}).on('mouseup mouseleave', function() {
    $('#signup_password').attr('type',"password");
});



// 메인페이지 진입전 로그인 확인 기능
function main_login_check() {
    if(localStorage.getItem("token") == null){
        alert('로그인 해주세요')
        location.href ="/";
    } else { location.href = "/main_page.html"; }
}


// 회원가입 기능
function sign_up() {
    let username = $('#nickname').val()
    let password =$('#signup_password').val()
    let info = {
        username:username,
        password:password,
    }
    if (username === "") {
        $("#help-id-login").text("닉네임을 입력해주세요.")
        $("#nickname").focus()
        return;
    } else {
        $("#help-id-login").text("")
    }
    if (password === "") {
        $("#help-password").text("비밀번호를 입력해주세요.")
        $("#signup_password").focus()
        return;
    }else {
        $("#help-password").text("")
    }

    $.ajax({
        type: 'POST',
        url: `/signup`,
        contentType: "application/json",
        data: JSON.stringify(info),
        success: function (response) {
            alert("회원가입이 완료되었습니다!!");
            location.href = '/';
        },
        error: function (error){
            alert("중복된 아이디가 있습니다")
        }
    })
}


// 회원가입 버튼 클릭시 중복 텍스트 숨기기 기능
function hide_nickname() {
    $("#can-using").hide()
    $("#cant-using").hide()
}


// 로그인 기능
function login() {
    let info = {
        username: $('#login_nickname').val(),
        password: $('#login_password').val()
    }
    $.ajax({
        type: 'POST',
        url: `/login`,
        contentType: "application/json",
        data: JSON.stringify(info),
        success: function (response) {
            alert("로그인완료")
            localStorage.setItem("token", response['token']);
            localStorage.setItem("username", response['username']);
            location.href = '/';

        },
        error: function (error){
            alert("아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.")
        }
    })
}

//로그아웃
function log_out() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    alert('로그아웃 되었습니다')
    location.href ="/";
}

// 회원가입시 닉네임 중복확인 기능
function nickname_check() {
    $("#cant-using").hide()
    $("#can-using").hide()
    let username = $('#nickname').val()

    let nick_name = {"username":username}

    if (username === "") {
        $("#help-id-login").text("닉네임을 입력해주세요.")
        $("#nickname").focus()
        return;
    } else {
        $("#help-id-login").text("")
    }
    $.ajax({
        type: "POST",
        url: "/check",
        contentType: 'application/json',
        data: JSON.stringify(nick_name),
        success: function (response) {
            if (response  == "사용할 수 있는 닉네임입니다.") {
                $("#cant-using").hide()
                $("#can-using").show()
            } else if (response == "중복되는 닉네임입니다. 다시 입력해주세요.") {
                $("#can-using").hide()
                $("#cant-using").show()
            }
        }
    });
}

//유저이름 가져오기
let user = localStorage.getItem("username")
let temp_html = `
<span >${user}님</span>
`
$('#usernames').append(temp_html)




// ajax 시 헤더 부분에 토큰 넣어주고 코드를 줄일 수 있다
$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
    if(localStorage.getItem('token')) {
        jqXHR.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('token'));
    }
});