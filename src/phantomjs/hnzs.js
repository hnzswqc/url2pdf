

var page = require('webpage').create();
page.open('http://www.jb51.net/article/48817.htm', function(success){

    if(success==='success'){
        console.log('success');
        page.render('E://0a9d3f25-a490-42b5-a3b9-550976554885.png');
        phantom.exit();

    }else{

        console.log('error');

        phantom.exit();

    }

});




