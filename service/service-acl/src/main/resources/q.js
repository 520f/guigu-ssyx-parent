function onApply() {
    window.openLayer("graduate_apply_AddOrModify.jsp", "新增毕业申请", "980px", "670px");
}

function callBackApply(rv) {
    if (rv == "ok") {
        onQuery();
    }
}

function onCancleApply() {
    var items = document.getElementsByName("checkbox");
//如果没有取到直接返回
    if (!items) return;
    var j = 0;
    var xh = "";
    for (var i = 0; i < items.length; i++) {
        if (items[i].checked) {
            xh += items[i].value + ",";
            if (document.getElementById("jfzt" + items[i].value).value != '0') {
                alert("请选择未缴费的数据！！");
                return;
            }
            if ($("#order_id" + items[i].value).val() != '') {
                alert("该数据已经生成订单，请选择未生成订单的数据！！");
                return;
            }
            j++;
        }
    }
    if (j == 0) {
        alert("请选择要操作的记录");
        return;
    }
    if (j > 1) {
        alert("请选择一条要操作的记录");
        return;
    }
    xh = xh.substr(0, xh.length - 1);
    var msg = "毕业申请已经结束，取消申请将不能再进行毕业申请，确定要操作吗?";
    if (document.getElementById("country_ispass" + xh).value == '1') {
        msg = "您的毕业申请初审已通过，是否确认要取消本次毕业申请?";
    }

    if (!confirm(msg)) {
        return;
    }
    var url = "/net/GraduateAction.do?method=onDel&xh=" + xh;
    $.ajax({
        url: url,
        async: true,
        success: function (result) {
            if (result == "ok") {
                alert("操作成功");
                onQuery();
            } else {
                alert("操作失败:" + result);
            }
        }
    });
}

function onUpdate() {
    var items = document.getElementsByName("checkbox");
    if (!items) return;
    var j = 0;
    var xh = "";
    for (var i = 0; i < items.length; i++) {
        if (items[i].checked) {
            xh += items[i].value + ",";
            if (document.getElementById("country_ispass" + items[i].value).value == '1') {
                alert("请选择未审核数据！！");
                return;
            }
            if ($("#order_id" + items[i].value).val() != '') {
                alert("该数据已经生成订单，请选择未生成订单的数据！！");
                return;
            }
            j++;
        }
    }
    if (j == 0) {
        alert("请选择要操作的记录");
        return;
    }
    if (j > 1) {
        alert("请选择一条要操作的记录");
        return;
    }
    xh = xh.substr(0, xh.length - 1);
    window.openLayer("graduate_apply_AddOrModify.jsp?applyXh=" + xh, "修改毕业申请", "980px", "670px");
}

function onUploadHzd() {
    var items = document.getElementsByName("checkbox");
    if (!items) return;
    var j = 0;
    var xh = "";
    for (var i = 0; i < items.length; i++) {
        if (items[i].checked) {
            xh += items[i].value + ",";
            if ($("#country_ispass" + items[i].value).val() != '1') {
                alert("请选择审核通过数据！！！");
                return;
            }
            if ($("#order_id" + items[i].value).val() != '') {
                alert("该数据已经生成订单，请选择未生成订单的数据！！");
                return;
            }
            if ($("#jfzt" + items[i].value).val() == '1') {
                alert("已缴费不允许上传！！");
                return;
            }
            j++;
        }
    }
    if (j == 0) {
        alert("请选择要操作的记录");
        return;
    }
    if (j > 1) {
        alert("请选择一条要操作的记录");
        return;
    }
    xh = xh.substr(0, xh.length - 1);
    window.openLayer("/net/pages/graduate/graduate_receipt_upload.jsp?applyXh=" + xh, "回执单上传", "980px", "670px");
//window.openLayer("graduate_receipt_upload.jsp?applyXh="+xh,"回执单上传", "980px","670px");
}

function lookView(xh) {
    window.openLayer("graduate_apply_AddOrModify.jsp?op=look&applyXh=" + xh, "查看毕业申请", "980px", "670px");
}

function onNetPay() {
    var items = document.getElementsByName("checkbox");
    if (!items) return;
    var j = 0;
    var xh = "";
    for (var i = 0; i < items.length; i++) {
        if (items[i].checked) {
            xh += items[i].value + ",";
            if ($("#country_ispass" + items[i].value).val() != '1') {
                alert("您的毕业申请还未现场初审，请等待申请区县（助学机构）初审通过后再进行网上支付！！！");
                return;
            }
            if ($("#jfzt" + items[i].value).val() != '0') {
                alert("请选择未缴费的数据！！");
                return;
            }
            if ($("#order_id" + items[i].value).val() != '') {
                alert("该数据已经生成订单，请选择未生成订单的数据！！");
                return;
            }
            j++;
        }
    }
    if (j == 0) {
        alert("请选择要操作的记录");
        return;
    }
    if (j > 1) {
        alert("请选择一条要操作的记录");
        return;
    }
    xh = xh.substr(0, xh.length - 1);
    var ajaxFlag = null;
    $.ajax({
        url: "/net/GraduateAction.do?method=receipt1&xh=" + xh,
        async: false,
        dataType: 'text',
        type: 'post',
        success: function (result) {
            ajaxFlag = result;
        }
    });
    if (ajaxFlag != 'ok') {
        alert("未上传回执单不允许缴费！");
        return false;
    }
    var money = $("#money" + xh).val();
    if (money == null || money == "") {
        money = "90";
    }
    var msg = "凡考生申请被审核过的，即使审核未通过而“退档”，毕业证书审定费一概不退，也不能作为再次参加审核的费用抵扣。是否确认支付操作？";
    if (!confirm(msg)) {
        return;
    }

    $("#examnum").val($("#examnum" + xh).val());
    $("#apply_xh").val(xh);
    $("#idcard").val($("#idcard" + xh).val());
    $("#money").val($("#money" + xh).val());
    $("#dataType").val($("#data_type" + xh).val());

    document.forms[0].action = "/net/NetPayAction.do?method=creatOrderByGraduateApply";
    document.forms[0].method = "post";
    document.forms[0].target = "r_hidden";
    parent.test("block");
    document.forms[0].submit();
    /*
    var url = "graduateApplyNetPay.jsp?apply_xh="+xh+"&examnum="+$("#examnum"+xh).val()+"&idcard="+$("#idcard"+xh).val()+"&money="+$("#money"+xh).val()+"&data_type="+$("#data_type"+xh).val();
    var r = window.showModalDialog(url,window,"status:no;resizable:no;dialogHeight:260px;dialogWidth:450px;unadorne:no");
    if(r != "" && r != null) {
    document.forms[0].action=r;//
    document.forms[0].method="post";
    document.forms[0].target = "_blank";
    document.forms[0].submit();
    onQuery();
    }*/
}

function refresh_order() {
    var examnum = "";
    var zkz = "010221100716";
    var url = "/net/NetPayAction.do?method=refresh_order&business_type=graduate&exam_num=202304&zkz=010221100716" + zkz;
    $.ajax({
        url: url,
        success: function (result) {
            if (result == "ok") {
                alert("操作成功");
                parent.test("none");
                onQuery();
            } else {
                parent.test("none");
            }
        }
    });
}

function sucessFresh(url) {
    if (url != null && url != "") {
        document.forms[0].action = url;//
        document.forms[0].method = "post";
        document.forms[0].target = "_blank";
        document.forms[0].submit();
    } else {
//window.location = window.location;
        onQuery();
    }
}

function onQuery() {
    document.forms[0].action = "graduate_main.jsp?op=query";//
    document.forms[0].method = "post";
    document.forms[0].target = "_self";
    document.forms[0].submit();
}

function onPrintHzd() {
    var items = document.getElementsByName("checkbox");
    if (!items) return;
    var j = 0;
    var xh = "";
    for (var i = 0; i < items.length; i++) {
        if (items[i].checked) {
            xh += items[i].value + ",";
            if ($("#country_ispass" + items[i].value).val() != '1') {
                alert("您的毕业申请还未现场初审，请等待申请区县（助学机构）初审通过后再进行操作！！！");
                return;
            }
            /* if($("#jfzt"+items[i].value).val()=='1' ) {
            alert("已缴费不允许打印！！");
            return;
            }*/

            j++;
        }
    }
    if (j == 0) {
        alert("请选择要操作的记录");
        return;
    }
    if (j > 1) {
        alert("请选择一条要操作的记录");
        return;
    }
    xh = xh.substr(0, xh.length - 1);

    window.open("/net/pages/graduate/graduate_apply_hzd.jsp?xh=" + xh);
}
	