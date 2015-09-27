function pressA(val){
	
	var proc = $('#totalExpectProc').val();
	$('#totalExpectProc').attr("lastVal", proc);
	var totalExpects = (proc == '' ? new Array() : proc);
	var expectObj = getExpect($('#totalExpect'));
	if(expectObj.count != 0){
		if(expectObj.item == 'A')
			totalExpects.push(expectObj.count);
		if(expectObj.item == 'B')
			totalExpects.push(-expectObj.count);
	} else 
		totalExpects.push(0);
	var proc = '';var sum=0;
	for(i=0; i<totalExpects.length; i++){
		if(totalExpects[i] != 0){
			sum += parseInt(totalExpects[i]);
			proc += totalExpects[i] >= 0 ? 
				'<font color="RED">+'+totalExpects[i]:'<font color="GREEN">'+totalExpects[i];
			proc += '</font>';
		}
	}
	proc += " = " + sum;
	$('#totalExpectProc').val(totalExpects);
	$('#totalExpectProc').html(proc);
	
	var countA=0;
	var countB=0;
	processALG1($('#ALG1'), 'A', val, 'POSITIVE');
	processALG1($('#ALG1N'), 'A', val, 'NEGTIVE');
	//processALG4($('#ALG4'), 'A', val);
	ALG4($('#ALG4'), 'A', val);
	NEWALG($('#NEWALG'), 'A', val, 'POSITIVE');
	//processNEWALG($('#NEWALG'), 'A', val, 'POSITIVE');
	NEWALG($('#NEWALGN'), 'A', val, 'NEGTIVE');
	//processNEWALG($('#NEWALGN'), 'A', val, 'NEGTIVE');
	$("span[name='algexpect']").each(function(){
		var expect = $(this).html();
		if(null != expect){
			var array = expect.split("*");
			if('0' != array[1] && 'A' == array[0])
				countA+=parseInt(array[1]);
			if('0' != array[1] && 'B' == array[0])
				countB+=parseInt(array[1]);
		}
	});//end of EACH
	
	var unit = $('#unit').val();
	if(countA>countB)
		$('#totalExpect').html("A*" + (countA-countB)*unit);
	if(countB>countA)
		$('#totalExpect').html("B*" + (countB-countA)*unit);
	if(countB==countA)
		$('#totalExpect').html("A=B=" + countA*unit);
}

function pressB(val){
	
	var proc = $('#totalExpectProc').val();
	$('#totalExpectProc').attr("lastVal", proc);
	var totalExpects = (proc == '' ? new Array() : proc);
	var expectObj = getExpect($('#totalExpect'));
	if(expectObj.count != 0){
		if(expectObj.item == 'B')
			totalExpects.push(expectObj.count);
		if(expectObj.item == 'A')
			totalExpects.push(-expectObj.count);
	} else 
		totalExpects.push(-expectObj.count);
	var proc = '';var sum=0;
	for(i=0; i<totalExpects.length; i++){
		if(totalExpects[i] != 0){
			sum += parseInt(totalExpects[i]);
			proc += totalExpects[i] >= 0 ? '<font color="RED">+'+totalExpects[i]:'<font color="GREEN">'+totalExpects[i];
			proc += '</font>';
		}
	}
	proc += " = " + sum;
	$('#totalExpectProc').val(totalExpects);
	$('#totalExpectProc').html(proc);
	
	var countA=0;
	var countB=0;
	
	processALG1($('#ALG1'), 'B', val, 'POSITIVE');
	processALG1($('#ALG1N'), 'B', val, 'NEGTIVE');
	
	//processALG4($('#ALG4'), 'B', val);
	ALG4($('#ALG4'), 'B', val);
	NEWALG($('#NEWALG'), 'B', val, 'POSITIVE');
	//processNEWALG($('#NEWALG'), 'B', val, 'POSITIVE');
	NEWALG($('#NEWALGN'), 'B', val, 'NEGTIVE');
	//processNEWALG($('#NEWALGN'), 'B', val, 'NEGTIVE');
	$("span[name='algexpect']").each(function(){
		var expect = $(this).html();
		if(null != expect){
			var array = expect.split("*");
			if('0' != array[1] && 'A' == array[0])
				countA+=parseInt(array[1]);
			if('0' != array[1] && 'B' == array[0])
				countB+=parseInt(array[1]);
		}
	});//end of EACH
	
	var unit = $('#unit').val();
	if(countA>countB)
		$('#totalExpect').html("A*" + (countA-countB)*unit);
	if(countB>countA)
		$('#totalExpect').html("B*" + (countB-countA)*unit);
	if(countB==countA)
		$('#totalExpect').html("A=B=" + countA*unit);
}

function pressBackspace(val){
	
	var lastVal = $('#totalExpectProc').attr("lastVal");
	if('' != lastVal){
		var lastVal = lastVal.split(',');
		var proc = '';var sum=0;
		for(i=0; i<lastVal.length; i++){
			if(lastVal[i] != 0){
				sum += parseInt(lastVal[i]);
				proc += lastVal[i] >= 0 ? '<font color="RED">+'+lastVal[i]:'<font color="GREEN">'+lastVal[i];
				proc += '</font>';
			}
		}
		proc += " = " + sum;
		$('#totalExpectProc').html(proc);
		$('#totalExpectProc').val(lastVal);
		if(lastVal.length != 0)
			lastVal.pop();
		$('#totalExpectProc').attr("lastVal", lastVal);
	} else {
		$('#totalExpectProc').val('');
		$('#totalExpectProc').html('');
	}

	var countA=0;
	var countB=0;
	processALG1($('#ALG1'), 'DEL', val, 'POSITIVE');
	processALG1($('#ALG1N'), 'DEL', val, 'NEGTIVE');
	//processALG4($('#ALG4'), 'DEL', val);
	ALG4($('#ALG4'), 'DEL', val);
	//processNEWALG($('#NEWALG'), 'DEL', val, 'POSITIVE');
	NEWALG($('#NEWALG'), 'DEL', val, 'POSITIVE');
	//processNEWALG($('#NEWALGN'), 'DEL', val, 'NEGTIVE');
	NEWALG($('#NEWALGN'), 'DEL', val, 'NEGTIVE');
	$("span[name='algexpect']").each(function(){
		var expect = $(this).html();
		if(null != expect){
			var array = expect.split("*");
			if('0' != array[1] && 'A' == array[0])
				countA+=parseInt(array[1]);
			if('0' != array[1] && 'B' == array[0])
				countB+=parseInt(array[1]);
		}
	});//end of EACH
	
	var unit = $('#unit').val();
	if(countA>countB)
		$('#totalExpect').html("A*" + (countA-countB)*unit);
	if(countB>countA)
		$('#totalExpect').html("B*" + (countB-countA)*unit);
	if(countB==countA)
		$('#totalExpect').html("A=B=" + countA*unit);
}