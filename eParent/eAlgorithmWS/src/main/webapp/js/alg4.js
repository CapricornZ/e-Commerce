//处理A，B，Backspace事件，返回期待值
//conatiner <-> ALG_6P, ALG_7P, ALG_8P, ALG_9P
function preProcessALG4Single(container, inputType){
	
	if('A' == inputType || 'B' == inputType){
		if(!container.find("div[toggle-class=switch]").jqxSwitchButton('disabled')){
			
			var check = container.find("div[toggle-class=switch]").attr("expect");
			var expect = container.find('#expects').html() + check;
			container.find('#expects').html(expect);
		}
	}
	if('DEL' == inputType){
		if(!container.find("div[toggle-class=switch]").jqxSwitchButton('disabled')){
			var expect = container.find('#expects').html();
			if(expect.length>0)
				expect = expect.substring(0, expect.length-1);
			container.find('#expects').html(expect);
		}
		var start = container.find("div[toggle-class=switch]").attr("start");
		if(null != start && start == 0){
			var expect = container.find('#expects').html();
			if(expect.length>0)
				expect = expect.substring(0, expect.length-1);
			container.find('#expects').html(expect);
		}
	}
	if('BLANK' == inputType){
	}
	var expect = container.find('#expects').html();
	return expect;
}

function postProcessALG4Single(container, data, inputType){
	
	if(data==null){
		container.find("div[toggle-class=switch]").jqxSwitchButton({ disabled:true });
		container.find("div[toggle-class=switch]").removeAttr("start");
		container.find("#expects").html("");
		container.find("#result").html("");
		container.find("#rowexpect").html("");
		return {'item':'X', 'count':0};

	} else {

		var rtn = data;
		if(rtn.expect != 0){
			container.find("div[toggle-class=switch]").jqxSwitchButton({ disabled:false });
			container.find("div[toggle-class=switch]").attr("start", -1);
		}
		else{
			container.find("div[toggle-class=switch]").jqxSwitchButton({ disabled:true });
			var count = parseInt(container.find("div[toggle-class=switch]").attr("start"));
			count += inputType=="DEL"?-1:+1;
			container.find("div[toggle-class=switch]").attr("start", count);
		}
		var expectItem = 'N/A';
		if(container.find("div[toggle-class=switch]").val())
			expectItem = rtn.expectItem;
		else{
			if('A' == rtn.expectItem)
				expectItem = 'B';
			if('B' == rtn.expectItem)
				expectItem = 'A';
		}
		//container.find("#result").html(rtn.formated + ' [' + expectItem + '*' + rtn.expect + ']');
		container.find("#result").html(rtn.formated);
		container.find("#rowexpect").html(expectItem + '*' + rtn.expect);
		return {'item':expectItem, 'count':rtn.expect};
	}
}

function obtainALG4Expect(container, source){
	
	var divExpects = new Array();
	container.find('#expects').each(function(){
		divExpects.push($(this));
	});
	
	$.post('ajax/alg/4/obtainExpects.do',
		{ "source" : source },
		function(data, status){
			for(i=0; i<data.length; i++)
				divExpects[i].html(data[i]);
		}).error(function(){
			for(i=0; i<divs.length; i++)
				divExpects[i].html("");
		});
}

function processALG4oxANDxo(container, inputType, source){
	
	var funcRtn = {'item':'N/A', 'count':0};
	$.post('ajax/alg/4/preAccept.do',
		{ "source" : source },
		function(data, status){//SUCCESS
	
			console.log(data);
			$('#sourceFormat').html(data[0]);
			container.find('#trueANDfalse').html(data[1]);
			
		}).error(function(){//ERROR
			console.log("ERROR");
		});//$.post
	
	var expectPatterns = new Array();
	var divs = new Array();
	container.find("div[name='ALGoxANDxo']").each(function(){
		divs.push($(this));
		expectPatterns.push(preProcessALG4Single($(this), inputType));
	});
	var obj = $.post('ajax/alg/4/oxANDxo/accept.do',
		{
			"source" : source,
			"expects": expectPatterns
		},
		function(data, status){
			
			var i = 0;
			var countA = 0;
			var countB = 0;
			for(i=0; i<divs.length; i++){
				var expectObj = postProcessALG4Single(divs[i], data[i], inputType);
				if(expectObj.count != 0){
					if('A'==expectObj.item)
						countA += expectObj.count;
					if('B'==expectObj.item)
						countB += expectObj.count;
				}
			}
			
			var rtn = {'item':'N/A', 'count':0};
			var expectSum = '';
			if(countA>countB){
				expectSum = 'A*'+(countA-countB);
				rtn = {'item':'A', 'count':countA-countB};
			}
			if(countA<countB){
				expectSum = 'B*'+(countB-countA);
				rtn = {'item':'B', 'count':countB-countA};
			}
			
			//var taf = container.find('#trueANDfalse').html();
			//container.find('#trueANDfalse').html(taf + ' [' + rtn.item + '*' + rtn.count + ']');
			container.find('#algexpect').html(rtn.item + '*' + rtn.count);
			//container.find('#algexpect').html(data[data.length-1]);
			funcRtn = rtn;
			return rtn;
		},"json").error(function(){
			console.log("ERROR");
			for(i=0; i<divs.length; i++){
				
				divs[i].find("div[toggle-class=switch]").jqxSwitchButton({ disabled:true });
				divs[i].find("div[toggle-class=switch]").removeAttr("start");
				divs[i].find("#expects").html("");
				divs[i].find("#result").html("");
				divs[i].find("#rowexpect").html("");
			}
			return {'item':'X', 'count':0};
		});
	return funcRtn;
}

function processALG4(container, inputType, source){
	
	var funcRtn = {'item':'N/A', 'count':0};
	$.post('ajax/alg/4/preAccept.do',
		{ "source" : source },
		function(data, status){//SUCCESS
	
			console.log(data);
			$('#sourceFormat').html(data[0]);
			container.find('#trueANDfalse').html(data[1]);
			
		}).error(function(){//ERROR
			console.log("ERROR");
		});//$.post
	
	var expectPatterns = new Array();
	var divs = new Array();
	container.find("div[name='ALG']").each(function(){
		divs.push($(this));
		expectPatterns.push(preProcessALG4Single($(this), inputType));
	});
	var obj = $.post('ajax/alg/4/accept.do',
		{
			"source" : source,
			"expects": expectPatterns
		},
		function(data, status){
			
			var i = 0;
			var countA = 0;
			var countB = 0;
			for(i=0; i<divs.length; i++){
				var expectObj = postProcessALG4Single(divs[i], data[i], inputType);
				if(expectObj.count != 0){
					if('A'==expectObj.item)
						countA += expectObj.count;
					if('B'==expectObj.item)
						countB += expectObj.count;
				}
			}
			
			var rtn = {'item':'N/A', 'count':0};
			var expectSum = '';
			if(countA>countB){
				expectSum = 'A*'+(countA-countB);
				rtn = {'item':'A', 'count':countA-countB};
			}
			if(countA<countB){
				expectSum = 'B*'+(countB-countA);
				rtn = {'item':'B', 'count':countB-countA};
			}
			
			//var taf = container.find('#trueANDfalse').html();
			//container.find('#trueANDfalse').html(taf + ' [' + rtn.item + '*' + rtn.count + ']');
			container.find('#algexpect').html(rtn.item + '*' + rtn.count);
			//container.find('#algexpect').html(data[data.length-1]);
			funcRtn = rtn;
			return rtn;
		},"json").error(function(){
			console.log("ERROR");
			for(i=0; i<divs.length; i++){
				
				divs[i].find("div[toggle-class=switch]").jqxSwitchButton({ disabled:true });
				divs[i].find("div[toggle-class=switch]").removeAttr("start");
				divs[i].find("#expects").html("");
				divs[i].find("#result").html("");
				divs[i].find("#rowexpect").html("");
			}
			return {'item':'X', 'count':0};
		});
	return funcRtn;
}

function ALG4(container, inputType, source, algType){
	
	var rtnAlg = new Array();
	rtnAlg.push(processALG4(container, inputType, source, algType));
	//rtnAlg.push(processALG4oxANDxo(container, inputType, source, algType));
	var countSubA = 0;
	for(i=0; i<rtnAlg.length; i++){
		if(rtnAlg[i].item == 'A')
			countSubA += rtnAlg[i].count;
		if(rtnAlg[i].item == 'B')
			countSubA -= rtnAlg[i].count;
	}
	if(countSubA > 0)
		container.find('#algexpect').html('A*' + countSubA);
	if(countSubA < 0)
		container.find('#algexpect').html('B*' + -countSubA);
	if(countSubA == 0)
		container.find('#algexpect').html('N/A*0');
}