

var page = require('webpage').create();
page.open(url, function(success){

    if(success==='success'){
        console.log('success');
        page.paperSize = { format: 'A4', 
                orientation: 'portrait', 
                border: '1cm' };
        page.render(savename);
        phantom.exit();

    }else{

        console.log('error');

        phantom.exit();

    }

});




