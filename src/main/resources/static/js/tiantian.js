var rotateFunc = function (awards, angle, text, tc, smlbbs,jifen,signcoin) {
    isture = true;
    $(".dazp").stopRotate();
    $(".dazp").rotate({
        angle: 0,
        duration: 1000, //旋转时间
        animateTo: angle + 3645, //让它根据得出来的结果加上3600度旋转
        callback: function () {
            isture = false; // 标志为 执行完毕
            $("body,html").addClass('overF'); //执行弹层
            $("body,html").addClass('overF');//禁止屏幕滚动
            $(".zhiz").removeAttr("disabled");
            $(".zhiz_img").css("display", "none");
            if (parseInt(awards) == 8) {
                if (smlbbs == '9'){
                    $('#reminder').text("网络异常,请稍后重试~");
                    $('.tc_wenxin').show();
                }else{
                    $('#reminder').text("哎呀，没有奖品出没~");
                    $('.tc_wenxin').show();
                }
                /*$('body,html').addClass('ovfHiden');*/
            } else if (smlbbs == '1') {
                $('.zj_tanceng').show();
            } else if(jifen == "1"){
                $('.tc_bg_jifen').show();//执行弹层
            }else if(signcoin == "1"){
                $('.tc_bg_signcoin').show();//执行弹层
            }else {
                $('.tc_bg').show();//执行弹层
            }
            var height1 = $(document).height();
            var height2 = $(window).height();
            var scroll_h = $(window).scrollTop();
            var tc_h = $("#" + tc + "").height();
            var tc_top = (height2 - tc_h) / 2 + scroll_h;
            $(".zj_jp_mes").text(text);
            $("#" + tc + "").css("top", tc_top + "px").show();
            $(".mc").height(height1).show();
            $(".caidai").show().animate({
                "top": "-0.2rem",
                "left": "-0.2rem",
                "width": "4.4rem",
                "opacity": "0"
            }, 2000)
        }
    });
};

$(".close_tc").click(function () {
    $(".tc").hide();
    $(".mc").hide();
    $(".caidai").hide().css({"top": "3.5rem", "left": "1.92rem", "width": "0.2rem", "opacity": "1"});
})


//底部轮播开始

$(function () {

    //新闻滚动部分
//新闻滚动开始
    var li_length_a = $('.wind').width();
    var ul_child_num = 20;
    $('.gundong_ul').css('width', li_length_a * ul_child_num);

    var now_width = $('.news_box').width();
    var yuanwidth = now_width;


    $('.gundong_ul').css('margin-left', now_width);

    setInterval(function () {
        now_width--;
        $('.gundong_ul').css('margin-left', now_width);

        if (now_width < -$('.gundong_ul').width()) {
            $('.gundong_ul').css('margin-left', $('.news_box').width());
            now_width = $('.news_box').width();
        }
    }, 40)
    //我的奖品
    $('.left_btn').click(function () {
        $('.annimate_left').show();
        $('.annimate_left').addClass('animated');
        $('.annimate_left').addClass('bounceInLeft');
    })

//清除奖品动画

    $('.zhuanpan_box').click(function () {
        $('.annimate_left').removeClass('animated');
        $('.annimate_left').removeClass('bounceInLeft');
        $('.annimate_left').hide();
    })
    $('.container_header_img').click(function () {
        $('.annimate_left').removeClass('animated');
        $('.annimate_left').removeClass('bounceInLeft');
        $('.annimate_left').hide();
    })
    $('.right_btn').click(function () {
        $('.annimate_left').removeClass('animated');
        $('.annimate_left').removeClass('bounceInLeft');
        $('.annimate_left').hide();
    })
    $('.swiper-container').click(function () {
        $('.annimate_left').removeClass('animated');
        $('.annimate_left').removeClass('bounceInLeft');
        $('.annimate_left').hide();
    })

    $('.zhuanpan_box_index').click(function () {
        $('.annimate_left').removeClass('animated');
        $('.annimate_left').removeClass('bounceInLeft');
        $('.annimate_left').hide();
    })
})

//灯光闪烁
$(function () {
    setInterval(function () {
        if ($('.zp_bg').attr('src') == '/dailylottery/images/zp_bg.png') {
            $(this).attr('src', '')
        }
    }, 1000)
})
//转盘灯
$(function () {
    setInterval(function () {
        var display_1 = $('#zhuanpan_1').css('display');

        if (display_1 == 'block') {
            $('#zhuanpan_1').hide();
            $('#zhuanpan_2').show();
        } else {
            $('#zhuanpan_1').show();
            $('#zhuanpan_2').hide();
        }
    }, 1000)
});

$(function () {


    $('.wenxin_guanbi_two,.wenxin_btn_two,.wenxin_btn1').click(function () {
        $('.tc_wenxin_two').hide();
        $('body,html').removeClass('overF');
    })

    $('.wenxin_guanbi_three,.wenxin_btn_three').click(function () {
        $('.tc_wenxin_three').hide();
        $('body,html').removeClass('overF');
    })

    $('.wenxin_guanbi_four,#cancel').click(function () {
        $('.tc_wenxin_four').hide();
        $('body,html').removeClass('overF');
    })

    //活动规则

    $('.huodongGzImg').click(function () {
        $('.hd_guize_mengceng').show();
        $('body,html').addClass('ovfHiden');
    })
    $('.guanbi_icon').click(function () {
        $('.hd_guize_mengceng').hide();
        $('body,html').removeClass('ovfHiden');
    })



});

