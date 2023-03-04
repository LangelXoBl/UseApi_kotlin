# UseAPi_kotlin

## Integrantes

* Luis Angel xool Balam. 202000098
* Glover Enrique Santos Concha. 202000092

## Modificaciones del codigo

se añadio el botton navigation con 3 secciones, uno para series de TV y otro para las peliculas del Top Rate

Glover: añadio la funcion `getTopRatedMovies` en el ApiServices para  hacer la petición de las peliculas
del Top Rated a la API. Tambien se modificaron el RemoteDataSource, el movieType y el repository para hacer la peticion de la API, 
la consulta a la DB y la insercción en la misma.

Luis: añadio el fragment para mostrar las vistas del Top Rated, junto con el adapter para usar el recyclearview y el view 
model para traer las peliculas cuando se cambie a la vista de Top Rated