package it.marconi.verificascattone.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmForm {
    
    private int id;
    private String titolo;
    private String genere;
    private int anno;
    private int voto;
}
