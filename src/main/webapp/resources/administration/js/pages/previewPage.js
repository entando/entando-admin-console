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

const handleChangeIframeUrl = (ev) => {
	const lang = ev && ev.detail || PROPERTY.lang;
	var normalizedBaseUrl = PROPERTY.baseUrl.replace(/\/$/, ''),
		previewUrl = [normalizedBaseUrl, 'preview', lang, PROPERTY.pageCode].join('/');
	previewUrl += '?' + [ 'token='+encodeURIComponent(PROPERTY.token) ].join('&');

	previewFrame.setAttribute('src', previewUrl);
}

const pageReady = () => {
	previewFrame = document.getElementById('previewFrame');
	const controlBar = document.getElementById('controlBar');
	controlBar.addEventListener(PreviewControlBarEvent.RESOLUTION_CHANGE, handleChangePreviewSize);
	controlBar.addEventListener(PreviewControlBarEvent.CHANGE_LANGUAGE, handleChangeIframeUrl);
	handleChangeIframeUrl();
	handleChangePreviewSize();
}

document.addEventListener("DOMContentLoaded", pageReady);
