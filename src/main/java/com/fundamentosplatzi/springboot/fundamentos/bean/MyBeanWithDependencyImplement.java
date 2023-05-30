package com.fundamentosplatzi.springboot.fundamentos.bean;

import com.fundamentosplatzi.springboot.fundamentos.FundamentosApplication;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class MyBeanWithDependencyImplement implements MyBeanWithDependency{
    Log LOGGER = LogFactory.getLog(MyBeanWithDependencyImplement.class);
    MyOperation myOperation;

    public MyBeanWithDependencyImplement(MyOperation myOperation) {
        this.myOperation = myOperation;
    }

    @Override
    public void printWithDependency() {
        LOGGER.info("Hemos ingresado al metodo printWithDependency"); //mostramos en el Log el nivel de info
        int numero=1;
        LOGGER.debug("El numero enviado como parametro a la dependencia operacion es: " + numero); //mostramos en el Log el nivel de debug
        System.out.println(myOperation.sum(numero));
        System.out.println("Hola desde la implementacion de un bean con dependencia");
    }
}
