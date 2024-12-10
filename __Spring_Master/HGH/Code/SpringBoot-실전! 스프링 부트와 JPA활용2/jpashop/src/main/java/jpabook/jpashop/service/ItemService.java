package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // 전체 readonly 어노테이션을 쓴다면 저장이 되지 않는다
    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        // 바뀌긴하지만, 권장되지 않는 방법이다. 유지보수하기가 힘들다
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);

        // 추천 방법
        //findItem.change(price, name, stockQuantity);

        // ... findItem에 세팅, 객체의 변화를 감지해서 메소드 호출 시 더티체킹이 이루어진다
        //...
        // Merge와 같은 동작을 하려면 아이템을 반환해야한다.
//        return findItem;
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
    // 굳이 repo의 메소드를 위임하는 클래스가 필요한지 생각해볼수있다. 컨트롤러에서 Repo로 연결되도 큰 문제가 되지 않는다.
}
