package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    private String createItem(Model model){

        model.addAttribute("form", new BookForm());

        return "items/createItemForm";
    }

    @PostMapping("items/new")
    private String createItemForm(Model model, BookForm form){

        Book book = new Book();

        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setIsbn(form.getIsbn());
        book.setAuthor(form.getAuthor());

        itemService.saveItem(book);

        return "redirect:/";
    }

    @GetMapping("items")
    public String itemsList(Model model){

        List<Item> items = itemService.findItems();

        model.addAttribute("items", items);

        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){

        Book item = (Book) itemService.findOne(itemId);

        BookForm bookForm = new BookForm();
        bookForm.setId(item.getId());
        bookForm.setAuthor(item.getAuthor());
        bookForm.setName(item.getName());
        bookForm.setIsbn(item.getIsbn());
        bookForm.setPrice(item.getPrice());
        bookForm.setStockQuantity(item.getStockQuantity());

        model.addAttribute("form" , bookForm);

        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable String itemId , @ModelAttribute("form") BookForm form){

        Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setAuthor(form.getAuthor());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setIsbn(form.getIsbn());
        System.out.println("=================================================");
        System.out.println("book.getId() = " + book.getId());
        System.out.println("form.getId() = " + form.getId());
        System.out.println("=================================================");
        itemService.saveItem(book);

        return "redirect:/items";
    }

}
