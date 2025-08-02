package com.projeto.sistema.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {

  @GetMapping("/hello")
  public String hello() {
    return "administrativo/home";
  }
  
}
