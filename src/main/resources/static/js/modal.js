//새로고침마다 로그인 상태에 따라 로그인/아웃이 바뀜
$(document).ready(function () {
    if(getCookie("access_token") == null){
        $('#login-button').show()
        $('#logout-button').hide()
    } else {
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

//쿠키 저장하기
var setCookie = function(name, value, exp) {
    var date = new Date();
    date.setTime(date.getTime() + exp*24*60*60*1000);
    document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/';
};

//쿠키 가져오기
var getCookie = function(name) {
    var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return value? value[2] : null;
};

//쿠키 삭제하기
var deleteCookie = function(name) {
    document.cookie = name + '=; expires=Thu, 01 Jan 1999 00:00:10 GMT;';
}


// 메인페이지 진입전 로그인 확인 기능
function main_login_check() {
    if(localStorage.getItem("token") == null){
        alert('로그인 해주세요')
        location.href ="/";
    } else { location.href = "/main_page.html"; }
}


// 회원가입 기능
function sign_up() {
    let info = {
        username:$('#nickname').val(),
        password:$('#signup_password').val(),
        email: $("#email").val()
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
    let nick_name = $('#nickname').val()
    console.log(nick_name)
    $.ajax({
        type: "POST",
        url: "/nickname",
        data: {
            nick_name: nick_name
        },
        success: function (response) {
            if (response['msg'] == "사용할 수 있는 닉네임입니다.") {
                $("#cant-using").hide()
                $("#can-using").show()
            } else if (response['msg'] == "중복되는 닉네임입니다. 다시 입력해주세요.") {
                $("#can-using").hide()
                $("#cant-using").show()
            }
        }
    });
}
//유저이름 가져오기
$("#username").html(localStorage.getItem("username"));


// ajax 시 헤더 부분에 토큰 넣어주고 코드를 줄일 수 있다
$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
    if(localStorage.getItem('token')) {
        jqXHR.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('token'));
    }
});