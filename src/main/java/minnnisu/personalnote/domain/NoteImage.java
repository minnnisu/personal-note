package minnnisu.personalnote.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
