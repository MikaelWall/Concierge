$(document).ready(function () {
    $('.header').height($(window).height());
})

$(".navbar a").click(function () {
    $("body,html").animate({
        scrollTop: $("#" + $(this).data('value')).offset().top
    }, 1000)

})
$(function () {
    $('#profile-image1').on('click', function () {
        $('#profile-image-upload').click();
    });
});

$(document).ready(function(){
    $("#edit-info").on("click",function(){
        
        $(".info-section").removeClass("no-edit-forms");
       $(this).hide();
       $("#cancel-info").removeClass("hide");
    });
    $("#cancel-info").on("click",function(){
        window.location.reload();
        
    });
     $(".upload-image").dropzone();
 });