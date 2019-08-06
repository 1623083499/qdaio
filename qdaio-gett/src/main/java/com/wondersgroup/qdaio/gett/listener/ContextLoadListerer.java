package com.wondersgroup.qdaio.gett.listener;

import com.wondersgroup.qdaio.gett.context.UsersCache;
import com.wondersgroup.qdaio.gett.dto.GettUserDto;
import com.wondersgroup.qdaio.gett.service.GettService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.List;

public class ContextLoadListerer implements ApplicationContextAware {
    private  static ApplicationContext applicationContext;

    @PostConstruct
  public  void postConstruct(){
  }

    public void contextInitialized() {
        GettUserDto gettUserDto=new GettUserDto();
        gettUserDto.setEnabled("1");
        List<GettUserDto> gettUserDtoList= applicationContext.getBean(GettService.class).queryAllUser(gettUserDto);
        for (GettUserDto gettUserDto1:gettUserDtoList){
            UsersCache.put(gettUserDto1);
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextLoadListerer.applicationContext=applicationContext;
        contextInitialized();
    }
}
