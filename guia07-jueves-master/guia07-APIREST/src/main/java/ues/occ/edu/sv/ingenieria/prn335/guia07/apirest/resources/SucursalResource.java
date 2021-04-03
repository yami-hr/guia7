/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.ingenieria.prn335.guia07.apirest.resources;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ues.occ.edu.sv.ingenieria.prn335.cineData.entity.Sucursal;

/**
 *
 * @author sonia
 */
@Path("sucursal")
@RequestScoped
public class SucursalResource implements Serializable {
    
    @Inject
    SucursalFacadeREST sucursalFacade;
    
    //Busqueda por rango, inicia por defecto desde cero y muestra la cantidad dada por defecto en parametro cantidad
    @GET
    @Path("/findrange/")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Sucursal> findRange( 
        @QueryParam("Inicio") @DefaultValue("0") int first,
        @QueryParam("cantidad") @DefaultValue("4") int pageSize){
               
        if(sucursalFacade !=null){
        return sucursalFacade.findRange(first, pageSize);
        }
        
         return Collections.EMPTY_LIST;
     
    }
    
    
    
    
    
    
    //este metodo devuelve todos los datos de la tabla de sucursal
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/findall/")
    public List<Sucursal> findAll(){
    if(sucursalFacade!=null){
    return sucursalFacade.findAll();
    }
    return Collections.EMPTY_LIST;
    
    }
    
    
    
    
    //metodo que devuelve la cantidad de registros en la entidad sucursal
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/count/")
    public int count(){
        
    if(sucursalFacade!=null){
    return sucursalFacade.count(); // retornara cantidad de registros
    }
    return 0; //retornara cero si esta vacio
    
    }
    
    
    
    @GET
   @Path("{id}") //buscar registro por su id
   @Produces({MediaType.APPLICATION_JSON})
   public Response findById(@PathParam("id") Long idmarca){
       if(idmarca!=null){
       try{
      List <Sucursal> encontrado=this.sucursalFacade.findById(idmarca);
       if(encontrado!=null){
       return Response.status(Response.Status.OK).entity(encontrado).build();
       }
       }catch(Exception e){}
       }
       return Response.status(Response.Status.NOT_FOUND).header("Registro no encontrado",idmarca).build();
   }
    
    
    
    
//   @GET 
//   @Path("/como/{like}")
//   @Produces({MediaType.APPLICATION_JSON})
//   public Response FindByLike(@PathParam("like") String like){
//    
//       if(sucursalFacade!=null){
//            try{
//               List<Sucursal> encontrado=this.sucursalFacade.findByLike(like);
//           if(encontrado!=null){
//               return Response.status(Response.Status.OK).entity(encontrado).build();
//           } 
//            }catch(Exception e){
//                
//            }
//           
//        }
//        return Response.status(Response.Status.NOT_FOUND).header("No se encontraron registros asi :v ", like).build();
//
//   }
//   
//   
//   
 
   
   //Agrega nueo registro
   @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void crear(Sucursal nueva) {
        try {
            if (sucursalFacade != null && nueva != null && nueva.getIdSucursal()== null) {
                sucursalFacade.create(nueva);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

  
    
   

    //elimina un registro
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void eliminar(@PathParam("id") int id) {
        try {
            if (sucursalFacade != null && id > 0) {
                sucursalFacade.remove(sucursalFacade.find(id));  //metodo para eliminar un registro segun el id indicado
            }
        } catch (Exception e) {
            System.out.println("ex: " + e);
        }
    }
   
    
    //para editar un registro en la tabla
     @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void editar(Sucursal nuevo) { 
        try {
            if (sucursalFacade != null && nuevo != null) {
                sucursalFacade.edit(nuevo);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
   
}
