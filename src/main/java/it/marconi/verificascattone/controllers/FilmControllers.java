package it.marconi.verificascattone.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.marconi.verificascattone.domains.FilmForm;
import it.marconi.verificascattone.services.FilmService;

@Controller
@RequestMapping("/films")
public class FilmControllers {
    
    
    @Autowired
    FilmService filmService;

    //endpoint che porta alla homepage con la lista di film
    @GetMapping()
    public ModelAndView viewHomepage() {
        return new ModelAndView("homepage");
    }

    //pagina per creare un nuovo film
    @GetMapping("/new")
    public ModelAndView newFilmForm() {
        return new ModelAndView("film-form").addObject("filmForm", new FilmForm());
    }

    //prevengo un rinvio dello stesso film facendo un redirect
    @PostMapping("/new")
    public ModelAndView handlerNewFilm(@ModelAttribute FilmForm filmForm) {

        //salvo il film nel finto database
        filmService.addFilm(filmForm);;

        int id = filmForm.getId();
        return new ModelAndView("redirect:/films/" + id);
    }

    //intercetto l'id e lo passo alla pagina dei dettagli
    @GetMapping("/films/{id}")
    public ModelAndView filmDetail(@PathVariable("id") int id, RedirectAttributes attr) {

        Optional<FilmForm> film = filmService.getFilmById(id);

        //controllo che il film sia presente e lo faccio vedere altrimenti avviso che non è presente
        if(film.isPresent()){
            attr.addFlashAttribute("added", true);
            return new ModelAndView("film-detail").addObject("film", film.get());
        }
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film non trovato");
    }

    //elimina film tamite id passato per url
    @GetMapping("/delete/{id}")
    public ModelAndView deleteFilm(@PathVariable("id") int id, RedirectAttributes attr) {

        filmService.deleteFilmById(id);

        attr.addFlashAttribute("deleted", true);

        return new ModelAndView("redirect:/films");
    }

    //svuoto il catalogo
    @GetMapping("deleteAll")
    public ModelAndView deleteAll(RedirectAttributes attr, ArrayList<FilmForm> filmList) {

        for(int i = 0; i <= filmList.size(); i++) {
            filmList.remove(i);
        }

        attr.addFlashAttribute("deletedAll", true);

        return new ModelAndView("redirect:/films");
    }
}
