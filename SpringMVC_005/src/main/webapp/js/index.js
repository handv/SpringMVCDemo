$(function () {
    $('a.nav-tab-btn').each(function() {
        if (this.href == document.location.href) {
            $(this).parent().addClass('active'); // this.className = 'active';
        }
    });
});
function submit_ajax(id, type) {
    $.ajaxSetup({
        cache: false //关闭AJAX相应的缓存
    });
    if (!$(".borrower_" + type).val() && !alert("请输入借用者名字")) return;
    $.ajax({
        type: 'post',
        url: '/devices/update/' + id,
        data: {borrower: borrower_name},//trim()去除借用者前后多余的空格
        dataType:'html',
        success: function (data) {
            if(data!="error") {
                var datas = data.split("#|$%|*", 2);
                $(".borrower_" + type).val(datas[0]);
                $("#owner_" + id).html(datas[0]);
                $("#time_" + id).html(datas[1]);
            }else{
                alert("该设备已属于借用者，请别闹");
            }
            //alert(data);
        },
        error: function (XMLHttpRequest) {
            alert(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
        }
    });
}