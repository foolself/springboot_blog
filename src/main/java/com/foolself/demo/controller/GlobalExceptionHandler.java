package com.foolself.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author http://foolself.github.io
 * @date 2018/10/26 19:39
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MultipartException.class)
    public String handleError1(MultipartException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", e.getCause().getMessage());
//        public String handleError1(MultipartException e, Model model) {
//            model.addAttribute("msg", e.getCause().getMessage());
        return "redirect:/userInfo";
    }
}