package ad.controller;


import ad.dto.GetAdResponse;
import ad.dto.GetAdsResponse;
import ad.dto.UpdateAdRequest;
import ad.entity.Ad;
import ad.service.AdService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/ads")
public class AdController {

    private AdService service;

    public AdController(){
    }

    @Inject
    public void setAdService(AdService service){this.service = service;}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAds(){
        return Response.ok(GetAdsResponse.entityToDtoMapper().apply(service.findAll())).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAd(@PathParam("id") Long id){
        Optional<Ad> ad = service.find(id);
        if(ad.isPresent()) {
            return Response.ok(GetAdResponse.entityToDtoMapper().apply(ad.get())).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putAd(@PathParam("id") Long id, UpdateAdRequest request){
        Optional<Ad> ad = service.find(id);
        if(ad.isPresent()){
            UpdateAdRequest.dtoToEntityUpdater().apply(ad.get(),request);
            service.update(ad.get());
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteAdType(@PathParam("id") Long id) {
        Optional<Ad> ad = service.find(id);
        if(ad.isPresent()){
            service.delete(ad.get().getId());
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
