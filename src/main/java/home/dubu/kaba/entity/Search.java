package home.dubu.kaba.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "search", indexes = @Index(name = "idx__keyword", columnList = "keyword"))
public class Search {
    @Id
    private String keyword;
    private int count;
    

    public void countUp() {
        this.count += 1;
    }
}
