/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.ingenieria.prn335.guia07.apirest.resources;

/**
 *
 * @author sonia
 */

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.annotation.JsonbTransient;
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
import ues.occ.edu.sv.ingenieria.prn335.guia07.apirest.resources.CaracteristicaAsientoFacadeREST;
import ues.occ.edu.sv.ingenieria.prn335.cineData.entity.CaracteristicaAsiento;
 
 



/**
 *
 * @author sonia
 */
    
@Path("Caracteristicaasiento")
@RequestScoped

public class CaracteristicaAsientoResource implements Serializable{
    
    @Inject
    CaracteristicaAsientoFacadeREST CAFacade;
    
    //Busqueda por rango, por defecto de 0 a 5
    @GET
    @Path("/findrange/")
    @Produces({MediaType.APPLICATION_JSON})
    public List<CaracteristicaAsiento> findRange( 
        @QueryParam("Inicio") @DefaultValue("0") int first,
        @QueryParam("cantidad") @DefaultValue("5") int pageSize){
               
        if(CAFacade!=null){
        return CAFacade.findRange(first, pageSize);
        }
        
         return Collections.EMPTY_LIST;
     
    }
    
    
    
    
    
    
    //metodo devuelve todos los datos en la entidad CA
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/findall/")
    public List<CaracteristicaAsiento> findAll(){
    if(CAFacade!=null){
    return CAFacade.findAll();
    }
    return Collections.EMPTY_LIST;
    
    }
    
    
    
    
    //metodo que devuelve la cantidad de registros
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/count/")
    public int count(){
        
    if(CAFacade!=null){
    return CAFacade.count(); // retornara cantidad de registros existentes
    }
    return 0; //sino hay retornara 0
    
    }
    
    
    //buscar registro por su id
    @GET
   @Path("{id}") 
   @Produces({MediaType.APPLICATION_JSON})
   public Response findById(@PathParam("id") Long idCaracteristica){
       if(idCaracteristica!=null){
       try{
      List <CaracteristicaAsiento> encontrado=this.CAFacade.findById(idCaracteristica);
       if(encontrado!=null){
       return Response.status(Response.Status.OK).entity(encontrado).build();
       }
       }catch(Exception e){}
       }
       return Response.status(Response.Status.NOT_FOUND).header("Registro no encontrado",idCaracteristica).build();
   }
    
    
    
    
   @GET 
   @Path("/como/{like}")
   @Produces({MediaType.APPLICATION_JSON})
   public Response FindByLike(@PathParam("like") String like){
    
       if(CAFacade!=null){
            try{
               List<CaracteristicaAsiento> encontrado=this.CAFacade.findByLike(like);
           if(encontrado!=null){
               return Response.status(Response.Status.OK).entity(encontrado).build();
           } 
            }catch(Exception e){
                
            }
           
        }
        return Response.status(Response.Status.NOT_FOUND).header("No se encontraron registros", like).build();

   }
   
   
   
   //CRUD
   
   
   //agrega un nuevo registro
   @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void crear(CaracteristicaAsiento nueva) {
        try {
            if (CAFacade != null && nueva != null && nueva.getCaracteristica()== null) {
                CAFacade.create(nueva);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

  
    
   

     //metodo para eliminar un registro por id
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void eliminar(@PathParam("id") int id) {
        try {
            if (CAFacade != null && id > 0) {
                CAFacade.remove(CAFacade.find(id)); 
            }
        } catch (Exception e) {
            System.out.println("ex: " + e);
        }
    }
   
    
    //editar un registro 
     @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void editar(CaracteristicaAsiento nuevo) { 
        try {
            if (CAFacade != null && nuevo != null) {
                CAFacade.edit(nuevo);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
   
}

    

