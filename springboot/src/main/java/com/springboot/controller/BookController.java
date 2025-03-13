package com.springboot.controller;




import com.github.pagehelper.PageInfo;
import com.springboot.common.Params;
import com.springboot.common.Result;
import com.springboot.entity.Book;
import com.springboot.service.AdminService;
import com.springboot.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hqf
 * @since 2025-03-01
 */
@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {
@Resource
    private BookService bookService;
    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<Book> info=bookService.findBySearch(params);
        return Result.success(info);
    }


    @PostMapping
    public Result save(@RequestBody Book book){
        if(book.getId()==null){
            bookService.add(book);
        }
        else {
            bookService.update(book);
        }
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        bookService.delete(id);
        return Result.success();
    }




}
