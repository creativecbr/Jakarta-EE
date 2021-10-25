package ad.controller;


import ad.dto.CreateAdRequest;
import ad.dto.GetAdResponse;
import ad.dto.UpdateAdRequest;
import ad.entity.Ad;
import ad.service.AdService;
import category.dto.GetAdsByCategoryResponse;
import category.entity.Category;
import category.service.CategoryService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Optional;

@Path("")
public class CategoryAdController {

    private AdService adService;

    private CategoryService categoryService;

    public CategoryAdController(){
    }

    @Inject
    public void setAdService(AdService adService){this.adService = adService;}

    @Inject
    public void setCategoryService(CategoryService categoryService){this.categoryService = categoryService;}

    @GET
    @Path("/categories/{name}/ads")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAds(@PathParam("name") String categoryName){
        Optional<Category> category = categoryService.find(categoryName);
        if(category.isPresent()){
            return Response.ok(GetAdsByCategoryResponse.entityToDtoMapper()
            .apply(adService.findAllByCategoryName(category.get().getName()))).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/categories/{name}/ads/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAd(@PathParam("name") String categoryName, @PathParam("id") Long id){
        Optional<Category> category = categoryService.find(categoryName);
        if(category.isPresent()){
            Optional<Ad> ad = adService.find(id);
            if(ad.isPresent()){
                if(ad.get().getCategory().getName().equals(categoryName)) {
                    return Response.ok(GetAdResponse.entityToDtoMapper().apply(ad.get())).build();
                }
                else {
                    return Response.status(Response.Status.CONFLICT).build();
                }
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/categories/{name}/ads")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postAd(@PathParam("name") String categoryName, CreateAdRequest request){
        Optional<Category> category = categoryService.find(categoryName);
        if(category.isPresent()){
            Ad ad = CreateAdRequest.dtoToEntityMapper().apply(request);
            adService.createWithCategory(ad, category.get());
            return Response.created(UriBuilder.fromMethod(CategoryAdController.class, "getAd").build(category.get().getName(),ad.getId())).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/categories/{name}/ads/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putAd(@PathParam("name") String categoryName, @PathParam("id") Long id, UpdateAdRequest request){
        Optional<Category> category = categoryService.find(categoryName);
        if(category.isPresent()){
            Optional<Ad> ad = adService.find(id);
            if(ad.isPresent()){
                if(ad.get().getId().equals(id))
                {
                    UpdateAdRequest.dtoToEntityUpdater().apply(ad.get(),request);
                    adService.update(ad.get());
                    return Response.status(Response.Status.OK).build();
                }
                else
                    return Response.status(Response.Status.CONFLICT).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/categories/{name}/ads/{id}")
    public Response deleteAd(@PathParam("name") String categoryName, @PathParam("id") Long id) {
        Optional<Category> category = categoryService.find(categoryName);
        if(category.isPresent()) {
            Optional<Ad> ad = adService.find(id);
            if(ad.isPresent()){
                adService.delete(ad.get().getId());
            }
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
