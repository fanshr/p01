$(function () {
    var shopId = 1;
    var listUrl = '/p01/shop/listProductsByShop?pageIndex=1&pageSize=9999&shopId='
        + shopId;
    var deleteUrl = '/p01/shop/modifyProduct';

    function getList() {
        $.getJSON(listUrl, function (response) {
            if (response.success) {
                console.log(response);
                var productList = response.data.productList;
                var tempHtml = '';
                productList.map(function (item, index) {
                    var textOp = "下架";
                    var contraryStatus = 0;
                    if (item.enableStatus == 0) {
                        textOp = "上架";
                        contraryStatus = 1;
                    } else {
                        contraryStatus = 0;
                    }
                    tempHtml += '' + '<div class="row row-product">'
                        + '<div class="col-30">'
                        + item.productName
                        + '</div>'
                        + '<div class="col-20">'
                        + item.priority
                        + '</div>'
                        + '<div class="col-50">'
                        + '<a href="#" class="edit" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">编辑</a>'
                        + '<a href="#" class="delete" data-id="'
                        + item.productId
                        + '" data-status="'
                        + contraryStatus
                        + '">'
                        + textOp
                        + '</a>'
                        + '<a href="#" class="preview" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">预览</a>'
                        + '</div>'
                        + '</div>';
                });
                $('.product-wrap').html(tempHtml);
            }
        });
    }

    getList();

    function deleteItem(id, enableStatus) {
        var product = {};
        product.productId = id;
        product.enableStatus = enableStatus;
        $.confirm('确定么?', function () {
            $.ajax({
                url: deleteUrl,
                type: 'POST',
                data: {
                    productStr: JSON.stringify(product),
                    statusChange: true
                },
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $.toast('操作成功！');
                        getList();
                    } else {
                        console.log(data);
                        $.toast('操作失败！');
                    }
                }
            });
        });
    }

    $('.product-wrap')
        .on(
            'click',
            'a',
            function (e) {
                var target = $(e.currentTarget);
                if (target.hasClass('edit')) {
                    window.location.href = '/p01/shop/productEdit?productId='
                        + e.currentTarget.dataset.id;
                } else if (target.hasClass('delete')) {
                    deleteItem(e.currentTarget.dataset.id,
                        e.currentTarget.dataset.status);
                } else if (target.hasClass('preview')) {
                    window.location.href = '/p01/frontend/productDetail?productId='
                        + e.currentTarget.dataset.id;
                }
            });

    $('#new').click(function () {
        window.location.href = '/p01/shop/productEdit';
    });
});


function getNewDate(dateStr, months) {
    let t1 = new Date(dateStr);
    t1.setMonth(months);
    let year = t1.getFullYear();
    let month = t1.getMonth() + 1;
    let day = t1.getDate();
    if (month < 10) month = "0" + month
    if (day < 10) month = "0" + day
    let result = year + "-" + month + "-" + day;
    return result;
}


function getNewDate(yearMonthDay, monthNum) {
    var arr = yearMonthDay.split('-');//2020-08-19或2020-08
    var year = parseInt(arr[0]);
    var month = parseInt(arr[1]);
    month = month + monthNum;
    if (month > 12) {//月份加
        var yearNum = parseInt((month - 1) / 12);
        month = month % 12 == 0 ? 12 : month % 12;
        year += yearNum;
    } else if (month <= 0) {//月份减
        month = Math.abs(month);
        var yearNum = parseInt((month + 12) / 12);
        year -= yearNum;
    }
    month = month < 10 ? "0" + month : month;
    return year + "-" + month + "-" + arr[2];
}



function getNewDate(date, monthCount) {
	var tempDate = new Date(date);
	var count = parseInt(monthCount);
	if(count < 0){
		return new Date();
	}
	var oldYear = tempDate.getFullYear();
	var oldMonth = tempDate.getMonth();
	var oldDate = tempDate.getDate();
	var newMonth = oldMonth + count;
	var newDate = new Date(oldYear, newMonth, oldDate);
	//防止月份数不一致，进行微调
	while (newDate.getMonth() != (newMonth % 12)) {
		oldDate--;
		newDate = new Date(oldYear, newMonth, oldDate);
	}

	var year = newDate.getFullYear();
	var month = newDate.getMonth() + 1;
	var day = newDate.getDate();
	if (month < 10) month = "0" + month
	if (day < 10) day = "0" + day
	var result = year + "-" + month + "-" + day;
	return result;
}


function getNewDate(date, monthCount) {
	var tempDate = new Date(date);
	var count = parseInt(monthCount);
	if(count < 0){
		return new Date();
	}
	var oldYear = tempDate.getFullYear();
	var oldMonth = tempDate.getMonth();
	var oldDate = tempDate.getDate();
	var newMonth = oldMonth + count;
	var newDate = new Date(oldYear, newMonth, oldDate);


	var year = newDate.getFullYear();
	var month = newDate.getMonth() + 1;
	var day = newDate.getDate();
	if (month < 10) month = "0" + month
	if (day < 10) day = "0" + day
	var result = year + "-" + month + "-" + day;
	return result;
}