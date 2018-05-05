/*
 * the first time to call
 */
setTimeout(function () {
    Pull();
//  alert("setTimeout called");
}, 200);

setInterval(function () {
    Pull();
    //alert("setInterval called");
}, 3000);

function Pull() {
    $.ajax({
        type: "Get",
        url: "/PollingServlet",
        data: {},
        beforeSend: function () {
        },
        success: function (data) {
            var obj = eval("(" + data + ")");//eval使用前要先加括号，才能得到完整的json数据
            console.log(obj);
            if (obj.notifications !== '0') {
                $('.notification-badge-container').html('通知中心' +
                    '<span class="new badge right" style="margin-top:2px">' + obj.notifications + '</span>');
                $('#notification-badge-container-index').html('<i class="material-icons">notifications_active</i>通知中心'
                    + '<span class="new badge right" style="margin-top:2px">' + obj.notifications + '</span>')
            } else {
                $('.notification-badge-container').html('通知中心')
                $('#notification-badge-container-index').html('<i class="material-icons">notifications_none</i>通知中心')
            }
            if (obj.messages !== '0') {
                $('.message-badge-container').html('我的私信' +
                    '<span class="new badge right" style="margin-top:2px">' + obj.messages + '</span>');
                $('#message-badge-container-index').html('<i class="material-icons">chat</i>我的私信\n' +
                    '<span class="new badge right" style="margin-top:2px">' + obj.messages + '</span>')
            } else {
                $('.message-badge-container').html('我的私信');
                $('#message-badge-container-index').html('<i class="material-icons">chat</i>我的私信\n')
            }
        }
    });
}