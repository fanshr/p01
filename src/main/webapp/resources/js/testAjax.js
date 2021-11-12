

jQuery.ajax({
    headers:{},
    type:'POST',
    url:'/api/test',
    data:{
        id:'',
    },
    dataType:'json',
    success:(response)=>{
        response = JSON.parse(response);
        const {status,error,payload} = response;
        if (!status){
            console.log(error);
            return;
        }
        console.log(response)
    },
    error:(e)=>{
        console.log(e);
    }

});


$.ajax({
    header:{},
    type:'POST',
    url:'/api/test',
    data: {
        id:''
    },
    dataType: 'json',
    success:(response)=>{

    },
    error:(e)=>{

    }
})


const options = {

}


let options = {
    url:'/api/test',
    method:'POST',
    param:{
        id:'test'
    },
    type:'text',
}
weaJs.callApi(options).then((response=>{

}))