$package("com.kidscademy");

/**
 * Build manager.
 */
com.kidscademy.BuildManager = {
	/**
	 * Increment build number.
	 *
	 * @param java.io.File projectDir,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return boolean
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 incrementBuildNumber: function(projectDir) {
		$assert(typeof projectDir !== "undefined", "com.kidscademy.BuildManager#incrementBuildNumber", "Project dir argument is undefined.");
		$assert(projectDir === null || js.lang.Types.isString(projectDir), "com.kidscademy.BuildManager#incrementBuildNumber", "Project dir argument is not a string.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.BuildManager#incrementBuildNumber", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.BuildManager#incrementBuildNumber", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.BuildManager", "incrementBuildNumber");
		rmi.setParameters(projectDir);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get dirty files.
	 *
	 * @param java.io.File projectDir,
	 * @param com.kidscademy.model.Excludes excludes,
	 * @param java.util.SortedMap<java.lang.String,byte[]> sourceFiles,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.model.DirtyFiles
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getDirtyFiles: function(projectDir, excludes, sourceFiles) {
		$assert(typeof projectDir !== "undefined", "com.kidscademy.BuildManager#getDirtyFiles", "Project dir argument is undefined.");
		$assert(projectDir === null || js.lang.Types.isString(projectDir), "com.kidscademy.BuildManager#getDirtyFiles", "Project dir argument is not a string.");
		$assert(typeof excludes !== "undefined", "com.kidscademy.BuildManager#getDirtyFiles", "Excludes argument is undefined.");
		$assert(typeof sourceFiles !== "undefined", "com.kidscademy.BuildManager#getDirtyFiles", "Source files argument is undefined.");
		$assert(sourceFiles === null || js.lang.Types.isArray(sourceFiles), "com.kidscademy.BuildManager#getDirtyFiles", "Source files argument is not an array.");

		var __callback__ = arguments[3];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.BuildManager#getDirtyFiles", "Callback is not a function.");
		var __scope__ = arguments[4];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.BuildManager#getDirtyFiles", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.BuildManager", "getDirtyFiles");
		rmi.setParameters(projectDir, excludes, sourceFiles);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Synchronize.
	 *
	 * @param java.io.File projectDir,
	 * @param java.util.List<java.lang.String> staleFiles,
	 * @param js.io.FilesInputStream freshFiles,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return boolean
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 synchronize: function(projectDir, staleFiles, freshFiles) {
		$assert(typeof projectDir !== "undefined", "com.kidscademy.BuildManager#synchronize", "Project dir argument is undefined.");
		$assert(projectDir === null || js.lang.Types.isString(projectDir), "com.kidscademy.BuildManager#synchronize", "Project dir argument is not a string.");
		$assert(typeof staleFiles !== "undefined", "com.kidscademy.BuildManager#synchronize", "Stale files argument is undefined.");
		$assert(typeof freshFiles !== "undefined", "com.kidscademy.BuildManager#synchronize", "Fresh files argument is undefined.");

		var __callback__ = arguments[3];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.BuildManager#synchronize", "Callback is not a function.");
		var __scope__ = arguments[4];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.BuildManager#synchronize", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.BuildManager", "synchronize");
		rmi.setParameters(projectDir, staleFiles, freshFiles);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Generate timestamp files list.
	 *
	 * @param java.io.File projectDir,
	 * @param com.kidscademy.model.Excludes excludes,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return boolean
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 generateTimestampFilesList: function(projectDir, excludes) {
		$assert(typeof projectDir !== "undefined", "com.kidscademy.BuildManager#generateTimestampFilesList", "Project dir argument is undefined.");
		$assert(projectDir === null || js.lang.Types.isString(projectDir), "com.kidscademy.BuildManager#generateTimestampFilesList", "Project dir argument is not a string.");
		$assert(typeof excludes !== "undefined", "com.kidscademy.BuildManager#generateTimestampFilesList", "Excludes argument is undefined.");

		var __callback__ = arguments[2];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.BuildManager#generateTimestampFilesList", "Callback is not a function.");
		var __scope__ = arguments[3];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.BuildManager#generateTimestampFilesList", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.BuildManager", "generateTimestampFilesList");
		rmi.setParameters(projectDir, excludes);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Deploy.
	 *
	 * @param java.lang.String productionServer,
	 * @param java.lang.String projectName,
	 * @param com.kidscademy.model.Excludes dirtyExcludes,
	 * @param com.kidscademy.model.Excludes mobileExcludes,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 deploy: function(productionServer, projectName, dirtyExcludes, mobileExcludes) {
		$assert(typeof productionServer !== "undefined", "com.kidscademy.BuildManager#deploy", "Production server argument is undefined.");
		$assert(productionServer === null || js.lang.Types.isString(productionServer), "com.kidscademy.BuildManager#deploy", "Production server argument is not a string.");
		$assert(typeof projectName !== "undefined", "com.kidscademy.BuildManager#deploy", "Project name argument is undefined.");
		$assert(projectName === null || js.lang.Types.isString(projectName), "com.kidscademy.BuildManager#deploy", "Project name argument is not a string.");
		$assert(typeof dirtyExcludes !== "undefined", "com.kidscademy.BuildManager#deploy", "Dirty excludes argument is undefined.");
		$assert(typeof mobileExcludes !== "undefined", "com.kidscademy.BuildManager#deploy", "Mobile excludes argument is undefined.");

		var __callback__ = arguments[4];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "com.kidscademy.BuildManager#deploy", "Callback is not a function.");
		var __scope__ = arguments[5];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.BuildManager#deploy", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.BuildManager", "deploy");
		rmi.setParameters(productionServer, projectName, dirtyExcludes, mobileExcludes);
		rmi.exec(__callback__, __scope__);
	}
};

if(typeof BuildManager === 'undefined') {
	BuildManager = com.kidscademy.BuildManager;
}
