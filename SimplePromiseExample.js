console.log("This is a javascript program!");

var simplePromise = new Promise(function(resolve, reject) {
	resolve("Successfully created a promise");
});

simplePromise
	.then(resolve => console.log(resolve))
	.catch(error => console.log(error))
	.finally(() => console.log("Simple promise has been completed"));
