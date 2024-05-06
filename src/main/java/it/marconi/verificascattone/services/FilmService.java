package it.marconi.verificascattone.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.marconi.verificascattone.domains.FilmForm;

@Service
public class FilmService {
    
    //creo un finto database
    private ArrayList<FilmForm> films = new ArrayList<>();

    //visualizzo la lista
    public ArrayList<FilmForm> getFilms() {
        return films;
    }

    //aggiungo un film
    public void addFilm(FilmForm newFilm) {
        films.add(newFilm);
    }

    //ricerco un film dal suo id
    public Optional<FilmForm> getFilmById(int id) {

        for(FilmForm f : films) {
            if(f.getId() == id) {
                return Optional.of(f);
            }
        }
        return Optional.empty();
    }

    //elimino un film tramite id
    public Optional<FilmForm> deleteFilmById(int id) {
        
        for(FilmForm f : films) {
            if(f.getId() == id) {
                films.remove(id);
            }
        }

        return Optional.empty();
    }

    //svuoto il "database"
    public void deleteAll(ArrayList<FilmForm> filmList) {
        filmList.clear();
    }
}
