var reload_pic = "file:///android_asset/reload_pic.png";
var default_pic = "file:///android_asset/default.png";

var picStr = '';			//所有图片拼接成的字符串
var splitKey = '_________'; //分割标志位
var array = new Array();
var first = true;

var  firstOpneUrl = true;
var UrlArray =  new Array(); 


String.prototype.endWith=function(endStr){
  var d=this.length-endStr.length;
  return (d>=0&&this.lastIndexOf(endStr)==d)
}
//获取所有的span a 标签 去除其样式 把样式隐藏掉
function rmspanStlye() {
//alert("======");
   var p = document.getElementsByTagName("p");         
			    for( var j =0; j<p.length; j++)                   
			    {                                                     
			        p[j].removeAttribute("style"); 
			        p[j].style.margin ="0";
		        }  


          var span = document.getElementsByTagName("span");         
		    for( var j =0; j<span.length; j++)                   
			    {                                                     
		 	        span[j].removeAttribute("style"); 
		         }   
		var pre =document.getElementsByName('pre');
		   for (var j=0;j<pre.length;j++){
				pre[j].removeAttribute("class")
				pre[j].removeAttribute("style")
			    pre[j].removeAttribute("id")				
				
				}	
				
			var font =document.getElementsByName("font");
		        for (var j=0;j<font.length;j++){
				font[j].removeAttribute("size")
			 	font[j].removeAttribute("style")
			     font[j].removeAttribute("face")		
			     font[j].removeAttribute("color")			
				}	
				
			var br =document.getElementsByName("br");
		        for (var j=0;j<br.length;j++){
				br[j].removeAttribute("size")
			 	br[j].removeAttribute("style")
			     br[j].removeAttribute("face")		
			     br[j].removeAttribute("color")			
				}	
				
			 	
			//	alert("font==  "+br.length);
				
				
		  var a = document.getElementsByTagName("a");         
			     for( var j =0; j<a.length; j++)                   
			    {                                                     
		        a[j].removeAttribute("style"); 
		        a[j].setAttribute("onclick", "openWebView(this)");
		       }   
		      var em = document.getElementsByTagName("em");         
			   
		    for( var j =0; j<em.length; j++)                   
			     {                                                     
			         em[j].removeAttribute("style"); 
		       } 
		         
		         // alert("一开始获取的===   "+document.getElementsByTagName("div")[0].innerHTML);
		       var html= document.getElementsByTagName("div")[0].innerHTML;  
		       html =html.replace(/((<font[^<]{2,}>)|(<span[^<]{2,}>)|(<br[^<]{2,}>)|(<div[^<]{2,}>)|(<pre[^<]{2,}>)|(<strong[^<]{2,}>))/g,"")
		        html =html.replace(/((<em>)|(<\/em>)|(<strong>)|(<\/strong>)|(<pre>)|(<\/pre>)|(<span>)|(<\/span>)|(<font>)|(<\/font>)|(<br>)|(<\/br>)|(<\/div>)|(<\/pre>))/g,'');
		        document.getElementsByTagName("body")[0].innerHTML= html;
		        
		  //    alert("正则替换过得 "+html);
		       //alert("没有替换的==  "+document.getElementsByTagName("body")[0].innerHTML);

 }

//获取点击列表

		//获取 点击链接
	function openWebView(urlStr) {
	var url =urlStr.href;
        window.wahtyopenurl.openWebView(url);
       }


function openImg(imgObj) {
    if (reload_pic.localeCompare(imgObj.src) == 0) {
        imgObj.setAttribute("src", default_pic);
        reoloadPic(imgObj);
    } else if (default_pic.localeCompare(imgObj.src) == 0) {
        // 当前为默认图片，无需操作
    } else {
    
    	//第一次点击初始化集合
        if (first) {
            for (i = 0; i < array.length; i++) {
                var img = array[i];
                if (img != null || img != undefined) {
                    picStr = picStr + splitKey + img.src; //拼接字符串
                }
            }
            first = false;
        }

		//当前图片是第几个
        var index = 0;
        for (i = 0; i < array.length; i++) {
            var img = array[i];
            if (img != null || img != undefined) {
                if (img == imgObj) {
                    break;			//找到跳出
                } else {
                    index++;
                }
            }
        }
        
        window.whaty.openImg(index, picStr, splitKey);
    }
}

// 重新加载图片的响应，重新调用loadImage,需要维护一个队列
var reloadQueue;
function reoloadPic(imgObj) {
	if (reloadQueue == undefined) {
    	reloadQueue = new Array();
  	}
  	reloadQueue.push(imgObj);
  	callReloadQueue();
}
// 执行重新加载图片队列
function callReloadQueue(){
	var reImg = reloadQueue.shift();
	if (reImg != undefined ) {
		loadImage(reImg.id,reImg.getAttribute("orisrc"),reloadImgResult);
	}
	
}
// 对核心方法loadResult进行包装，代理模式的一种，为重新加载图片使用
var reloadImgResult = function(data) {
  loadResult(data);
  callReloadQueue();
}



// app通知html开始加载图片
function startLoadImg() {
	traversalImg();
}
// 对核心方法loadResult进行包装，代理模式的一种，为初始加载图片使用
var initImgResult = function(data) {
  loadResult(data);
  traversalImg();
}


var imgs;
var imgPos = 0;
function traversalImg(){
  if (imgs == undefined) {
    imgs = document.getElementsByClassName("aimg");
  }
  if (imgPos < imgs.length) {
    loadImage(imgs[imgPos].id,imgs[imgPos].getAttribute("orisrc"),initImgResult);
    imgPos++;
  }
}




// 以下两个方法为预加载图片核心方法，预加载和重新加载都会使用
function loadImage(id,src,callback)
{
  var imgloader= new window.Image();
  //当图片成功加载到浏览器缓存
  imgloader.onload =function(evt)  
  {

    if(typeof(imgloader.readyState)=='undefined')
    {
      imgloader.readyState = 'undefined';
    }
    if ((imgloader.readyState=='complete'||imgloader.readyState=="loaded")||imgloader.complete)   
    { 
      callback({'msg':'ok','src':src,'id':id});
    }else{
      imgloader.onerror(evt);
    }
  };
 
  imgloader.onerror = function(evt)
  {
     callback({'msg':'error','id':id});
  };
                        
  
  imgloader.src=src;
}

// 预加载成功或失败的回调
var loadResult = function(data)
{
  data =   data ||{} ;
  if(typeof(data.msg)!='undefined')
  {
    if(data.msg=='ok')
    {
      //这里使用了id获取元素，有点死板，建议读者自行扩展为css 选择符
      document.getElementById(''+data.id).src=data.src;
    }else{
      //这里图片加载失败，我们可以显示其他图片，防止大红叉
      document.getElementById(''+data.id).src=reload_pic;
      
    }
  }
}


//给图片添加事件同时处理宽度
function prettyImage() {
    var imgs = document.getElementsByTagName("img");
   
    for (i = 0; i < imgs.length; i++) {
    	var img = imgs[i];
    	img.setAttribute("id", i);
        img.setAttribute("onerror", "this.src='"+default_pic+"'");//加载失败的默认图片
        img.setAttribute("onload", "updateImageAfterload(this);");//加载完成回调
    }
}

//图片加载完成后更新属性
function updateImageAfterload(img) {
    var cssText = "width:100%";
     var screenW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth||window.screen.width;
    //var screenW = document.documentElement.clientWidth;//用这个值更准确些
    
 	//宽度大小0.3倍屏幕宽度就可以点击开
	if(img.width > screenW*0.5){
		var src = img.src;
		if(src!=null && src!=undefined && !src.endWith('gif')){
			img.setAttribute("onclick", "openImg(this)");
       		array[img.id] = img; 
		}
        
	}
	
	// 宽度大于屏幕宽度就设置100%的宽度属性
	if (img.width > screenW && img.width>150) { 
        img.removeAttribute("height");
        img.removeAttribute("style");
        img.setAttribute("style", cssText);
    }
    
    //用来调试的方法， 往webInfo里加自己的调试信息
    //document.getElementById('webInfo').innerHTML = document.getElementById('webInfo').innerHTML+'<br/>'+img.width + '----'+window.innerWidth + '---'+document.documentElement.clientWidth+'---'+document.body.clientWidth+'----'+window.screen.width;
	
}
