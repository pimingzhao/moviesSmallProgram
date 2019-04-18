import {HTTP} from "../utils/http";
class MovieModel extends HTTP{
    getInTheaters(){
        return this.request({
            url:"movies/inTheaters/query"
        })
    }
    getTop250(){
        return this.request({
            url:"movies/top250/query"
        })
    }
    getComingSoon(){
        return this.request({
            url:"movies/comingSoon/query"
        })
    }
}
export {MovieModel};