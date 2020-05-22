package fun.gengzi.codecopy.utils;

import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

public class MappingJacksonJsonView extends MappingJackson2JsonView {
 
	@Override
	protected Object filterModel(Map<String, Object> model) {
		Map<?, ?> result = (Map<?, ?>) super.filterModel(model);    
        if (result.size() == 1) {    
            return result.values().iterator().next();    
        } else {    
            return result;    
        }    
	}

}
