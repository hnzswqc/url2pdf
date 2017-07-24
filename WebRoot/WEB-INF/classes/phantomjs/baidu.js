

var page = require('webpage').create();
page.open('http://www.baidu.com', function(success){

    if(success==='success'){
        console.log('success');
        page.render('baidu.png');
        phantom.exit();

    }else{

        console.log('error');

        phantom.exit();

    }

});




