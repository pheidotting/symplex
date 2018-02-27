define(['moment'],
    function(moment) {

    return {
        valideerDatum: function(datum) {
        	if(datum === undefined || datum === null || datum === ''){
        		return true;
        	}
        	return datum === moment(datum, "DD-MM-YYYY").format("DD-MM-YYYY");
        },

		isDatumVoorAndereDatum: function(datumVoor, datumNa) {
			if(!this.valideerDatum(datumVoor) || !this.valideerDatum(datumNa)){
				return false;
			}

			if((moment(datumVoor).diff(moment(datumNa))) < 0){
				return true;
			}

			return false;
		}
    };
});