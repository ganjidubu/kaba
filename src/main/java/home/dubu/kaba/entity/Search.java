package home.dubu.kaba.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "search", indexes = @Index(name = "idx__keyword", columnList = "keyword"))
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String keyword;
    private int count;


    public Search(String keyword, int count) {
        this.keyword = keyword;
        this.count = count;
    }


    public void countUp() {
        this.count += 1;
    }
}
