function checkForm() {
    var username = document.getElementById('username');
    var inputPwd = document.getElementById('input_pwd');
    var md5Pwd = document.getElementById('md5_pwd');
    var inputed = true;
    [
        [username,function (value) {return value === ''}],
        [inputPwd,function (value) {return value === ''}]
    ].forEach(function (item) {
        var value = item[0].value.trim();
        if(item[1](value)){
            item[0].classList.add('z-err');
            inputed = false;
        }
        item[0].value = value;
    });
    if(inputed){
        md5Pwd.value = md5(inputPwd.value);
        return true;
    }else
        return false;
}