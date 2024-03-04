package minnnisu.personalnote.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteImage {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Note note;

    private String imageName;

    public NoteImage(Note note, String imageName) {
        this.note = note;
        this.imageName = imageName;
    }
}
