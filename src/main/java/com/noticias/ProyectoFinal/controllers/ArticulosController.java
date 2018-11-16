package com.noticias.ProyectoFinal.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.noticias.ProyectoFinal.bean.Articulo;
import com.noticias.ProyectoFinal.bean.Categoria;
import com.noticias.ProyectoFinal.bean.Comentario;
import com.noticias.ProyectoFinal.bean.Suscripcion;
import com.noticias.ProyectoFinal.bean.Tag;
import com.noticias.ProyectoFinal.bean.Usuario;
import com.noticias.ProyectoFinal.service.ArticuloSERVICE;
import com.noticias.ProyectoFinal.service.CategoriaSERVICE;
import com.noticias.ProyectoFinal.service.ComentarioSERVICE;
import com.noticias.ProyectoFinal.service.EmailSERVICE;
import com.noticias.ProyectoFinal.service.TagSERVICE;
import com.noticias.ProyectoFinal.service.UsuarioSERVICE;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

@Controller
public class ArticulosController {
	
	@Autowired
	ArticuloSERVICE articuloSERVICE;
	
	@Autowired
	CategoriaSERVICE categoriaSERVICE;
	
	@Autowired
	UsuarioSERVICE usuarioSERVICE;
	
	@Autowired
	TagSERVICE tagSERVICE;
	
	@Autowired
	ComentarioSERVICE comentarioSERVICE;
	
	@Autowired
	EmailSERVICE emailSERVICE;
	
	@Value("${app.url.client}")
	String urlClient;
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/articulos")
	public ModelAndView articulos (@RequestParam(value="error", required = false) String error,
									@RequestParam(value="success", required = false) String success,
									Model model) {
			
		if (error != null) {
			model.addAttribute("error", error);
		}
		
		if (success != null) {
			model.addAttribute("success", success);
		}
		
		
		return new ModelAndView("articulos", "listaArticulos", articuloSERVICE.listadoArticulos());
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/filtraArticulos")
	public ModelAndView filtraArticulos (@RequestParam(value="titulo", required = false) String titulo, Model model) {
			
		model.addAttribute("tituloBuscado", titulo);
		
		if (titulo == "") 			
			return new ModelAndView("articulos", "listaArticulos", articuloSERVICE.listadoArticulos());			
		else 			
			return new ModelAndView("articulos", "listaArticulos", articuloSERVICE.getsArticulosByTitulo(titulo));			
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/info_articulo")
	public ModelAndView info_articulo (@RequestParam(name="idArticulo")String idArticulo, Model model) {
			
		List<Tag> listadoTags = tagSERVICE.getTagsByIdArticulo(Integer.parseInt(idArticulo));
		
		if (listadoTags.size() > 0) {
		
			List<Categoria> listadoCategoriasCheck = new ArrayList<Categoria>();
			List<Categoria> listadoCategoriasNoCheck = new ArrayList<Categoria>();
			
			List<Integer> listadoIdCategoria = new ArrayList<Integer>();
			
			for (Tag tag : listadoTags) {
				
				listadoCategoriasCheck.add(tag.getCategoriaTag());	
				listadoIdCategoria.add(tag.getCategoriaTag().getIdCategoria());
			}
			
			listadoCategoriasNoCheck = categoriaSERVICE.getsCategoriasNotInIdCategoria(listadoIdCategoria);
			
			model.addAttribute("listadoCategoriasCheck", listadoCategoriasCheck);	
			model.addAttribute("listadoCategoriasNoCheck", listadoCategoriasNoCheck);
			
		} else {
			
			model.addAttribute("listadoCategoriasNoCheck", categoriaSERVICE.listadoCategorias());
		}
		
		return new ModelAndView("info_articulo", "articulo", articuloSERVICE.getArticuloById(Integer.parseInt(idArticulo)));
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/imagesArticulo/{idArticulo}")
	@ResponseBody
	public byte[] muestraImagenArticulo(Model model, @PathVariable("idArticulo")Integer idArticulo, 
										@RequestParam(name="error", required=false)String error) {

		if (error != null) {
			
			model.addAttribute("error", error);
		}
		
	    Articulo articulo = articuloSERVICE.getArticuloById(idArticulo);
	    return articulo.getImagen();
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/nuevoArticulo")
	public String nuevoArticulo (@RequestParam(name="error", required=false)String error, Model model) {
		
		if (error != null) {
			model.addAttribute("error", error);
		}
		
		model.addAttribute("listaCategorias", categoriaSERVICE.listadoCategorias());	
	    return "nuevoArticulo";
	}
	
	//Subida de imagen y asignar categorias
	@Secured("ROLE_ADMIN")
	@PostMapping("/crearArticulo")
	public ModelAndView crearArticulo (@RequestParam(name="titulo")String titulo, @RequestParam(name="descripcion")String descripcion, 
									   @RequestParam(name="contenido") String contenido, Authentication authentication,
									   @RequestParam(name="categorias", required=false) List<String> categorias,
									   HttpServletRequest request) throws ServletException, IOException {
		
		List<Categoria> categoriasArticuloNuevo = new ArrayList<Categoria>();
		Usuario usuario = usuarioSERVICE.getUsuarioByLogin(authentication.getName());
		
		// Tratamiento de la imagen
		Part filePart = request.getPart("imagen");
		InputStream inputS = null;
		ByteArrayOutputStream os = null;
		
		if (!getFileName(filePart).endsWith(".jpg")  && !getFileName(filePart).endsWith(".jpeg") && !getFileName(filePart).endsWith(".png")) {
			
			return new ModelAndView("redirect:/nuevoArticulo", "error", "Error: solo se admiten formatos de imagen");
		}
		
		if (!getFileName(filePart).equals("")) {
			
			inputS = filePart.getInputStream();

			// Escalar la imagen
			BufferedImage imageBuffer = ImageIO.read(inputS);
			
			if (imageBuffer == null) {
				
				return new ModelAndView("redirect:/nuevoArticulo", "error", "Error: imagen corrupta");
			}
						
			Image tmp = imageBuffer.getScaledInstance(imageBuffer.getWidth(), imageBuffer.getHeight(), BufferedImage.SCALE_REPLICATE);
			BufferedImage buffered = new BufferedImage(imageBuffer.getWidth(), imageBuffer.getHeight(), BufferedImage.TYPE_INT_RGB);
			buffered.getGraphics().drawImage(tmp, 0, 0, null);

			os = new ByteArrayOutputStream();
			ImageIO.write(buffered, "jpg", os);
		}
		
		Articulo articulo = new Articulo(titulo, os.toByteArray(), descripcion, contenido);
		
		
		if (authentication != null)
			articulo.setUsuarioAdmin(usuario);
		
		articuloSERVICE.guardaArticulo(articulo);
		
		if (categorias != null) {
						
			for (String idCategoria : categorias) {
			
				Tag tag = new Tag();
				tag.setArticuloTag(articulo);
				tag.setCategoriaTag(categoriaSERVICE.getCategoriaById(Integer.parseInt(idCategoria)));
				tagSERVICE.guardaTag(tag);
				
				categoriasArticuloNuevo.add(categoriaSERVICE.getCategoriaById(Integer.parseInt(idCategoria)));
			}
		}
	
		
		for (Categoria categoria : categoriasArticuloNuevo) {
			
			for (Suscripcion suscripcion : usuario.getSuscripciones()) {
				
				if (suscripcion.getCategoriaSuscrita().getNombre().equals(categoria.getNombre())) {
					emailSERVICE.enviaEmail
					(
						suscripcion.getUsuarioSuscriptor().getEmail(), 
						"Nuevo articulo de categoria " + categoria.getNombre(), 
						"Estimado " + usuario.getNombre() + " : Nueva noticia"
					);
				}
			}
		}	
		
		return new ModelAndView("redirect:/articulos", "success", "Info: articulo creado con éxito");
	}
	
	//Subida de imagen y asignar categorias
	@Secured("ROLE_ADMIN")
	@PostMapping("/actualizaArticulo")
	public ModelAndView actualizaArticulo (@RequestParam(name="idArticulo")String idArticulo, @RequestParam(name="titulo") String titulo, 
									   @RequestParam(name="descripcion")String descripcion, @RequestParam(name="contenido") String contenido, 
									   @RequestParam(name="categoriasCheck", required=false) List<String> categoriasCheck,
									   @RequestParam(name="categoriasNoCheck", required=false) List<String> categoriasNoCheck,
									   HttpServletRequest request,
									   @RequestParam("imagen") MultipartFile file) throws ServletException, IOException {
		
		Articulo articulo = articuloSERVICE.getArticuloById(Integer.parseInt(idArticulo));
		
//		Tratamiento de la imagen
		Part filePart = request.getPart("imagen");
		InputStream inputS = null;
		ByteArrayOutputStream os = null;
		
		if (!getFileName(filePart).isEmpty()) {		
		
			if (!getFileName(filePart).endsWith(".jpg")  && !getFileName(filePart).endsWith(".jpeg") && !getFileName(filePart).endsWith(".png")) {
				
				return new ModelAndView("redirect:/info_articulo?idArticulo=" + articulo.getIdArticulo(), "error", "Error: solo se admiten formatos de imagen");
			}
			
			
			if (!getFileName(filePart).equals("")) {
				inputS = filePart.getInputStream();
	
				// Escalar la imagen
				BufferedImage imageBuffer = ImageIO.read(inputS);
				
				if (imageBuffer == null) {
					
					return new ModelAndView("redirect:/info_articulo?idArticulo=" + articulo.getIdArticulo(), "error", "Error: imagen corrupta");
				}
				
				Image tmp = imageBuffer.getScaledInstance(640, 640, BufferedImage.SCALE_FAST);
				BufferedImage buffered = new BufferedImage(640, 640, BufferedImage.TYPE_INT_RGB);
				buffered.getGraphics().drawImage(tmp, 0, 0, null);
	
				os = new ByteArrayOutputStream();
				ImageIO.write(buffered, "jpg", os);
			}		
		}
		
		articulo.setTitulo(titulo);
		articulo.setDescripcion(descripcion);
		articulo.setContenido(contenido);
		
		if (os != null)
			articulo.setImagen(os.toByteArray());
		
		//Actualizo articulo
		articuloSERVICE.actualizaArticulo(articulo);
		
		//Elimino todos los tags antiguos asociados a este articulo
		List<Tag> listadoTags = tagSERVICE.getTagsByIdArticulo(articulo.getIdArticulo());
		for (Tag tag : listadoTags)
			tagSERVICE.eliminaTag(tag.getIdTag());
		
		//Insertamos tags seleccionados
		if (categoriasCheck != null) {
			
			for (String idCategoria : categoriasCheck) {
				
				Tag tag = new Tag();
				tag.setArticuloTag(articulo);
				tag.setCategoriaTag(categoriaSERVICE.getCategoriaById(Integer.parseInt(idCategoria)));
				tagSERVICE.guardaTag(tag);
			}
		}
		
		if (categoriasNoCheck != null) {
			
			for (String idCategoria : categoriasNoCheck) {
				
				Tag tag = new Tag();
				tag.setArticuloTag(articulo);
				tag.setCategoriaTag(categoriaSERVICE.getCategoriaById(Integer.parseInt(idCategoria)));
				tagSERVICE.guardaTag(tag);
			}
		}
		
		return new ModelAndView("redirect:/articulos", "success", "Info: articulo actualizado con éxito");
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/elimina_articulo")
	public ModelAndView eliminaArticulo(@RequestParam(name="idArticulo")String idArticulo) {
	    		
		
		Articulo articulo = articuloSERVICE.getArticuloById(Integer.parseInt(idArticulo));
		
		if (!articulo.getTags().isEmpty()) {		
			
			for (Tag tag : articulo.getTags()) 				
				tagSERVICE.eliminaTag(tag.getIdTag());
		}
		
		if (!articulo.getComentarios().isEmpty()) {
			
			for (Comentario comentario : articulo.getComentarios())
				comentarioSERVICE.eliminaComentario(comentario.getIdComentario());
		}
		
		articuloSERVICE.eliminaArticulo(articulo.getIdArticulo());
	    return new ModelAndView("redirect:/articulos", "success", "Info: articulo eliminado con éxito");
	}
	
	private String getFileName(Part filePart) {
		
		for(String content : filePart.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename"))
				return content.substring(content.indexOf("=")+1).trim().replace("\"", "");
		}
		return null;
	}
}
