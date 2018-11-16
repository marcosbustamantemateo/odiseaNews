package com.noticias.ProyectoFinal.controllers;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.noticias.ProyectoFinal.bean.Categoria;
import com.noticias.ProyectoFinal.bean.Suscripcion;
import com.noticias.ProyectoFinal.bean.Tag;
import com.noticias.ProyectoFinal.service.CategoriaSERVICE;
import com.noticias.ProyectoFinal.service.SuscripcionSERVICE;
import com.noticias.ProyectoFinal.service.TagSERVICE;

@Controller
public class CategoriaController {
	
	@Autowired
	CategoriaSERVICE categoriaSERVICE;
	
	@Autowired
	SuscripcionSERVICE suscripcionSERVICE;
	
	@Autowired
	TagSERVICE tagSERVICE;
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/categorias")
	public ModelAndView categorias (@RequestParam(value="error", required = false) String error,
									@RequestParam(value="success", required = false) String success,
									Model model) {
		
		if (error != null) {
			model.addAttribute("error", error);
		}
		
		if (success != null) {
			model.addAttribute("success", success);
		}
			
		return new ModelAndView("categorias", "listaCategorias", categoriaSERVICE.listadoCategorias());
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/imagesCategoria/{idCategoria}")
	@ResponseBody
	public byte[] muestraImagenCategoria (Model model, @PathVariable("idCategoria")Integer idCategoria) {
		
		Categoria categoria = categoriaSERVICE.getCategoriaById(idCategoria);
	    return categoria.getImagen();
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/info_categoria")
	public ModelAndView infoCategoria (@RequestParam(name="idCategoria")String idCategoria, @RequestParam(name="error", required=false)String error,
										Model model) {
		
		if (error != null) {
			
			model.addAttribute("error", error);
		}
		
	    return new ModelAndView("info_categoria", "categoria", categoriaSERVICE.getCategoriaById(Integer.parseInt(idCategoria)));
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/nuevaCategoria")
	public String nuevaCategoria (@RequestParam(name="error", required=false)String error, Model model) {
		
		if (error != null) {
			model.addAttribute("error", error);
		}
		
	    return "nuevaCategoria";
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/crearCategoria")
	public ModelAndView crearCategoria (@RequestParam(name="nombre")String nombre, HttpServletRequest request) throws ServletException, IOException {
		
		// Tratamiento de la imagen
		Part filePart = request.getPart("imagen");
		InputStream inputS = null;
		ByteArrayOutputStream os = null;
		
		if (!getFileName(filePart).endsWith(".jpg")  && !getFileName(filePart).endsWith(".jpeg") && !getFileName(filePart).endsWith(".png")) {
			
			return new ModelAndView("redirect:/nuevaCategoria", "error", "Error: solo se admiten formatos de imagen");
		}
		
		if (!getFileName(filePart).equals("")) {
			inputS = filePart.getInputStream();

			// Escalar la imagen
			BufferedImage imageBuffer = ImageIO.read(inputS);
			
			if (imageBuffer == null) {
				
				return new ModelAndView("redirect:/nuevaCategoria", "error", "Error: imagen corrupta");
			}
			
			Image tmp = imageBuffer.getScaledInstance(imageBuffer.getWidth(), imageBuffer.getHeight(), BufferedImage.SCALE_REPLICATE);
			BufferedImage buffered = new BufferedImage(imageBuffer.getWidth(), imageBuffer.getHeight(), BufferedImage.TYPE_INT_RGB);
			buffered.getGraphics().drawImage(tmp, 0, 0, null);

			os = new ByteArrayOutputStream();
			ImageIO.write(buffered, "jpg", os);
		}
		
		Categoria categoria =new Categoria(nombre, os.toByteArray());
		
		if (categoriaSERVICE.existeCategoria(categoria)) {
			
			return new ModelAndView("nuevaCategoria", "error", "Error: categoria ya existente");
		}
		
		categoriaSERVICE.guardaCategoria(categoria);
		
	    return new ModelAndView("redirect:/categorias", "success", "Info: categoria creada con éxito");
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/actualizaCategoria")
	public ModelAndView actualizaCategoria (@RequestParam(name="idCategoria")String idCategoria, 
										@RequestParam(name="nombre")String nombre, HttpServletRequest request,
										@RequestParam("imagen") MultipartFile file) throws ServletException, IOException {
		
		
		Categoria categoria = categoriaSERVICE.getCategoriaById(Integer.parseInt(idCategoria));
		
//		Tratamiento de la imagen
		Part filePart = request.getPart("imagen");
		InputStream inputS = null;
		ByteArrayOutputStream os = null;
		
		if (!getFileName(filePart).isEmpty()) {		
		
			if (!getFileName(filePart).endsWith(".jpg")  && !getFileName(filePart).endsWith(".jpeg") && !getFileName(filePart).endsWith(".png")) {
				
				return new ModelAndView("redirect:/info_categoria?idCategoria=" + categoria.getIdCategoria(), "error", "Error: solo se admiten formatos de imagen");
			}
			
			if (!getFileName(filePart).equals("")) {
				inputS = filePart.getInputStream();
	
				// Escalar la imagen
				BufferedImage imageBuffer = ImageIO.read(inputS);
				
				if (imageBuffer == null) {
					
					return new ModelAndView("redirect:/info_categoria?idCategoria=" + categoria.getIdCategoria(), "error", "Error: imagen corrupta");
				}
				
				Image tmp = imageBuffer.getScaledInstance(640, 640, BufferedImage.SCALE_FAST);
				BufferedImage buffered = new BufferedImage(640, 640, BufferedImage.TYPE_INT_RGB);
				buffered.getGraphics().drawImage(tmp, 0, 0, null);
	
				os = new ByteArrayOutputStream();
				ImageIO.write(buffered, "jpg", os);
			}
		}		
		categoria.setNombre(nombre);
		
		if (os != null)
			categoria.setImagen(os.toByteArray());
		
		categoriaSERVICE.actualizaCategoria(categoria);
		
		return new ModelAndView("redirect:/categorias", "success", "Info: categoria actualizada con éxito");
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/elimina_categoria")
	public ModelAndView eliminaCategoria (@RequestParam(name="idCategoria")String idCategoria) {
		
		Categoria categoria = categoriaSERVICE.getCategoriaById(Integer.parseInt(idCategoria));
		
		if (!categoria.getSuscripciones().isEmpty()) {
			
			for (Suscripcion suscripcion : categoria.getSuscripciones()) 				
				suscripcionSERVICE.eliminaSuscripcion(suscripcion.getIdSuscripcion());
		}
		
		if (!categoria.getTags().isEmpty()) {
			
			for (Tag tag : categoria.getTags())
				tagSERVICE.eliminaTag(tag.getIdTag());
		}
			
		categoriaSERVICE.eliminaCategoria(categoria.getIdCategoria());	

	    return new ModelAndView("redirect:/categorias", "success", "Info: categoria eliminada con éxito");
	}
	
	private String getFileName(Part filePart) {
		
		for(String content : filePart.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename"))
				return content.substring(content.indexOf("=")+1).trim().replace("\"", "");
		}
		return null;
	}
}
