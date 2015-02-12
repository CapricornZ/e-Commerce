package elements.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import elements.algorithm.IAlgorithm;

@Controller
@RequestMapping("/ajax")
public class AjaxController {
	
	@Qualifier("ALG12")
	@Autowired
	private IAlgorithm alg;
	
	@RequestMapping("/helloworld")
	@ResponseBody
    public Result helloworld(@RequestParam("source")String source) {

		Result rtn = new Result();
		rtn.setElements(new ArrayList<Element>());
		
		Element element = this.alg.nextItem(source);
		System.out.println(String.format("%s expects [%s]", source, element.getNextItem()));

		rtn.getElements().add(element);
        return rtn;
    }
}