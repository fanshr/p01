$(function () {

    function getlist(e) {
        $.ajax({
            url: "/p01/shop/list",
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    handleList(data.shopList);
                    handleUser(data.user);
                }
            }
        });
    }

    function handleUser(data) {
        $('#user-name').text(data.name);
    }

    function handleList(data) {
        var html = '';
        data.map(function (item, index) {
            html += '<div class="row row-shop"><div class="col-40">' + item.shopName + '</div><div class="col-40">' + shopStatus(item.enableStatus) + '</div><div class="col-20">' + goShop(item.enableStatus, item.shopId) + '</div></div>';

        });
        $('.shop-wrap').html(html);
    }

    function goShop(status, id) {
        if (status != 0 && status != -1) {
            return '<a href="/p01/shop/shopManage?shopId=' + id + '">进入</a>';
        } else {
            return '';
        }
    }

    function shopStatus(status) {
        if (status == 0) {
            return '审核中';
        } else if (status == -1) {
            return '店铺非法';
        } else {
            return '审核通过';
        }
    }


    $('#log-out').click(function () {
        $.ajax({
            url: "/p01/shop/logout",
            type: "post",
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    window.location.href = '/p01/shop/ownerlogin';
                }
            },
            error: function (data, error) {
                alert(error);
            }
        });
    });


    getlist();
});
