const sizeOptions = {
	desktop: {
		width: '100%',
		height: '100%'
	},
	tablet: {
		width: '768px',
		height: '1024px'
	},
	smartphone: {
		width: '360px',
		height: '640px'
	}
};

var previewFrame;

const handleChangePreviewSize = (ev) => {
	const { width, height } = ev && ev.detail || {
		width: PROPERTY.previewWidth,
		height: PROPERTY.previewHeight,
	};
	previewFrame.setAttribute('width', width.replace(/px/i, ''));
	previewFrame.setAttribute('height', height.replace(/px/i, ''));
};

const handleChangeIframeUrl = () => {
	var normalizedBaseUrl = PROPERTY.baseUrl.replace(/\/$/, ''),
		previewUrl = [normalizedBaseUrl, 'preview', PROPERTY.lang, PROPERTY.pageCode].join('/');
	previewUrl += '?' + [ 'token='+encodeURIComponent(PROPERTY.token) ].join('&');

	previewFrame.setAttribute('src', previewUrl);
}

const pageReady = () => {
	previewFrame = document.getElementById('previewFrame');
	const controlBar = document.getElementById('controlBar');
	controlBar.addEventListener(PreviewControlBarEvent.RESOLUTION_CHANGE, handleChangePreviewSize);
	handleChangeIframeUrl();
	handleChangePreviewSize();
}

document.addEventListener("DOMContentLoaded", pageReady);


/*
$(function () {

	var sizeMap = {
		desktop: {
			width: '100%',
			height: '100%'
		},
		tablet: {
			width: '768px',
			height: '1024px'
		},
		smartphone: {
			width: '360px',
			height: '640px'
		}
	};
	var customSizes = {};

	var $customPanel = $('.custom-panel'),
		$customWidthInput = $customPanel.find('.custom-width'),
		$customHeightInput = $customPanel.find('.custom-height'),
		$customOkBtn = $('.custom-size-btn'),
		$previewModeSelect = $('.preview-mode-select'),
		$langSelect = $('#preview-mode-lang'),
		$previewFrame = $('iframe#previewFrame');



	function updatePreviewSize() {
		var key = $previewModeSelect.val(),
			width, height;

		if (key === 'custom') {
			$customWidthInput.removeAttr('disabled');
			$customHeightInput.removeAttr('disabled');

			customSizes = {
				width: customSizes.width || $customWidthInput.val() || 100,
				height: customSizes.height || $customHeightInput.val() || 100
			};
			width = customSizes.width + 'px';
			height = customSizes.height + 'px';
			$customWidthInput.val(customSizes.width);
			$customHeightInput.val(customSizes.height);

		} else if (key && sizeMap[key]) {
			$customWidthInput.attr('disabled', 'disabled');
			$customHeightInput.attr('disabled', 'disabled');

			width = sizeMap[key].width;
			height = sizeMap[key].height;
			$customWidthInput.val(width);
			$customHeightInput.val(height);
		}
		$previewFrame
			.attr('width', width)
			.attr('height', height);

	}


	function updateIframeUrl() {
		var normalizedBaseUrl = PROPERTY.baseUrl.replace(/\/$/, ''),
		previewUrl = [normalizedBaseUrl, 'preview', PROPERTY.lang, PROPERTY.pageCode].join('/');
		previewUrl += '?' + [ 'token='+encodeURIComponent(PROPERTY.token) ].join('&');
	
		$previewFrame.attr('src', previewUrl);
	}
	
	
	updateIframeUrl();
	$customWidthInput.val(PROPERTY.previewWidth);
	$customHeightInput.val(PROPERTY.previewHeight);
	$previewModeSelect.val('custom');
	$langSelect.val(PROPERTY.lang);

	updatePreviewSize();



	$previewModeSelect.change(function () {
		updatePreviewSize();
	});
	
	$langSelect.change(function(){
		PROPERTY.lang = $langSelect.val();
		updateIframeUrl();
		
	});

	$customOkBtn.click(function() {
		updatePreviewSize();
	});

});
*/